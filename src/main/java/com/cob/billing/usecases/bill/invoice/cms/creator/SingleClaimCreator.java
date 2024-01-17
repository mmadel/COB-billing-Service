package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.checker.ServiceLineExceedChunkChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class SingleClaimCreator {

    @Autowired
    FilesClaimCreator filesClaimCreator;

    public List<String> create(List<PatientInvoiceEntity> invoices,
                               InvoiceRequest invoiceRequest) throws IOException {
        List<String> fileNames = new ArrayList<>();
        generate(invoices, invoiceRequest, fileNames);
        return fileNames;
    }

    private void generate(List<PatientInvoiceEntity> invoices,
                          InvoiceRequest invoiceRequest, List<String> fileNames) throws IOException {

        List<List<PatientInvoiceEntity>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(invoices);
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<PatientInvoiceEntity> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "claim.pdf" + "_" + i;
            filesClaimCreator.create(fileName, invoiceRequest, invoicesChunk, fileNames);
        }
    }
}
