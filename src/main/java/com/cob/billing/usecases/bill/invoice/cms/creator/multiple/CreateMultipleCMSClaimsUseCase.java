package com.cob.billing.usecases.bill.invoice.cms.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.cob.billing.util.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("MultipleClaimItems")
public class CreateMultipleCMSClaimsUseCase implements CMSClaimCreator {
    public Map<String,List<SelectedSessionServiceLine>> create(InvoiceRequest invoiceRequest,Boolean[] flags) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        Map<String,List<SelectedSessionServiceLine>> result = new HashMap<>();
        if(flags[0])
            result.putAll(BeanFactory.getBean(CreateCMSProviderUseCase.class)
                    .create(invoiceRequest));
        if(flags[1])
            result.putAll(BeanFactory.getBean(CreateCMSClinicUseCase.class)
                    .create(invoiceRequest));
        if(flags[2])
            result.putAll(BeanFactory.getBean(CreateCMSPatientCaseUseCase.class)
                    .create(invoiceRequest));
        if(flags[3])
            result.putAll(BeanFactory.getBean(CreateCMSDatesUseCase.class)
                    .create(invoiceRequest));
        if(flags[4])
            result.putAll(BeanFactory.getBean(CreateCMSAuthorizationUseCase.class)
                    .create(invoiceRequest));

        return result;
    }
}
