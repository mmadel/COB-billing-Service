package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateCMSDocumentUseCase {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    CreateCMSPdfDocumentResourceUseCase createCMSPdfDocumentResourceUseCase;

    public String create(InvoiceRequest invoiceRequest) throws IOException {
        String fileName = "test.pdf";
        //createCMSPdfDocumentResourceUseCase.create(invoiceRequest, fileName);
        return fileName;
    }


}
