package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.checker.ServiceLineExceedChunkChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimsCaseCreator {

    @Autowired
    FilesClaimCreator filesClaimCreator;

    Map<String, List<PatientInvoiceEntity>> cases;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if (isMultiple()) {
            for (Map.Entry<String, List<PatientInvoiceEntity>> entry : cases.entrySet()) {
                generate(entry.getKey(), entry.getValue(), invoiceRequest, fileNames);
            }
        }
        return fileNames;
    }

    private void group(List<PatientInvoiceEntity> patientInvoiceRecords) {
        cases =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getCaseTitle()));
    }

    private void generate(String caseTitle, List<PatientInvoiceEntity> invoices,
                          InvoiceRequest invoiceRequest,
                          List<String> fileNames) throws IOException {
        List<List<PatientInvoiceEntity>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(invoices);
        for (int i = 0; serviceLinesChunks.size() > i; i++) {
            List<PatientInvoiceEntity> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "case_" + caseTitle + "_" + i;
            filesClaimCreator.create(fileName, invoiceRequest, invoicesChunk, fileNames);
        }
    }

    private boolean isMultiple() {
        return cases.size() > 1 ? true : false;
    }
}
