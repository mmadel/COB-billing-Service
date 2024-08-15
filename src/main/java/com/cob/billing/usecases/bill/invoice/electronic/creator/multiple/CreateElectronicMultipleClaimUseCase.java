package com.cob.billing.usecases.bill.invoice.electronic.creator.multiple;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.util.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("MultipleElectronicClaim")
public class CreateElectronicMultipleClaimUseCase implements ElectronicClaimCreator {

    @Override
    public List<Claim> create(InvoiceRequest invoiceRequest, Boolean[] flags) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        List<Claim> claims = new ArrayList<>();
        if (flags[0])
            claims.addAll(BeanFactory.getBean(CreateElectronicProviderUseCase.class)
                    .create(invoiceRequest));
        if (flags[1])
            claims.addAll(BeanFactory.getBean(CreateElectronicClinicsUseCase.class)
                    .create(invoiceRequest));
        if (flags[2])
            claims.addAll(BeanFactory.getBean(CreateElectronicCasesUseCase.class)
                    .create(invoiceRequest));
        if (flags[3])
            claims.addAll(BeanFactory.getBean(CreateElectronicDatesUseCase.class)
                    .create(invoiceRequest));
        if (flags[4])
            claims.addAll(BeanFactory.getBean(CreateElectronicAuthorizationUseCase.class)
                    .create(invoiceRequest));
        return claims;
    }
}
