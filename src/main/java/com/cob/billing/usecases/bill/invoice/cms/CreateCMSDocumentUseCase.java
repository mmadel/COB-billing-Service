package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateCMSDocumentUseCase {

    @Autowired
    ClaimCreator claimCreator;

    @Autowired
    MultipleClaimCreator multipleClaimCreator;

    public List<String> createCMSDocument(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {

        List<String> fileNames = new ArrayList<>();
        fileNames.addAll(multipleClaimCreator.create(invoiceRequest));
        if (!(fileNames.size() > 1)) {
            claimCreator.setInvoiceRequest(invoiceRequest);
            fileNames.addAll(claimCreator.create());
        }
        return fileNames;
    }

}
