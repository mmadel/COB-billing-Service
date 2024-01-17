package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.usecases.bill.invoice.cms.checker.ServiceLineExceedChunkChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimsDateCreator {
    @Autowired
    FilesClaimCreator filesClaimCreator;

    private Map<Long, List<PatientInvoiceEntity>> dates;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if (isMultiple() &&
                isDatePerClaim(invoiceRequest.getInvoiceRequestConfiguration())) {
            for (Map.Entry<Long, List<PatientInvoiceEntity>> entry : dates.entrySet()) {
                generate(entry.getKey(), entry.getValue(), invoiceRequest, fileNames);
            }
        }
        return fileNames;
    }

    private void group(List<PatientInvoiceEntity> patientInvoiceRecords) {
        dates = patientInvoiceRecords.stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getServiceDate()));
    }

    private boolean isMultiple() {
        return dates.size() > 1;
    }

    private void generate(Long date, List<PatientInvoiceEntity> invoices,
                          InvoiceRequest invoiceRequest,
                          List<String> fileNames) throws IOException {
        List<List<PatientInvoiceEntity>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(invoices);
        for (int i = 0; serviceLinesChunks.size() > i; i++) {
            List<PatientInvoiceEntity> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "date_" + date + "_" + i;
            filesClaimCreator.create(fileName,invoiceRequest,invoicesChunk,fileNames);
        }
    }

    private boolean isDatePerClaim(InvoiceRequestConfiguration invoiceRequestConfiguration) {
        if (invoiceRequestConfiguration.getIsOneDateServicePerClaim() != null) {
            return invoiceRequestConfiguration.getIsOneDateServicePerClaim();
        } else {
            return false;
        }
    }
}
