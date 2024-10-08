package com.cob.billing.usecases.bill.invoice.electronic.creator.single;

import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.response.tmp.InvoiceResponse;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.electronic.creator.CreateElectronicFieldsUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@Service
@Qualifier("SingleElectronicClaim")
public class CreateElectronicSingleClaimUseCase implements ElectronicClaimCreator {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;
    @Override
    public List<Claim> create(InvoiceRequest invoiceRequest, Boolean[] flags, InvoiceResponse invoiceResponse) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return createElectronicFieldsUseCase.create(invoiceRequest,invoiceResponse);
    }
}
