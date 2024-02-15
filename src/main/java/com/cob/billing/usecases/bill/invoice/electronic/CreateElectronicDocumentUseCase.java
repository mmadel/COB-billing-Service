package com.cob.billing.usecases.bill.invoice.electronic;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicMultipleClaimCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateElectronicDocumentUseCase {
    @Autowired
    ElectronicClaimCreator claimCreator;
    @Autowired
    ElectronicMultipleClaimCreator multipleClaimCreator;

    public List<Claim> createDocument(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        claims.addAll(multipleClaimCreator.create(invoiceRequest));
        if (!(claims.size() > 1)) {
            claimCreator.setInvoiceRequest(invoiceRequest);
            claims.add(claimCreator.create());
        }
        return claims;
    }
}
