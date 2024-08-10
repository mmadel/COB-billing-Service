package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.factory.CMSClaimFactory;
import com.cob.billing.usecases.bill.invoice.cms.creator.initiator.ClaimInitiator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateCMSDocumentUseCase {
    public List<String> createCMSDocument(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        ClaimInitiator claimInitiator = CMSClaimFactory.getInstance(invoiceRequest);
        List<String> fileNames = claimInitiator.create(invoiceRequest);
        return fileNames;
    }

}
