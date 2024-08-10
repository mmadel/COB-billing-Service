package com.cob.billing.usecases.bill.invoice.cms.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.models.ClaimMultipleItems;
import com.cob.billing.usecases.bill.invoice.cms.creator.models.MultipleItemsWrapper;
import com.cob.billing.util.BeanFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CreateMultipleCMSClaimsUseCase {
    public List<String> create(InvoiceRequest invoiceRequest,Boolean[] flags) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        if(flags[0])
            fileNames.addAll(BeanFactory.getBean(CreateCMSProviderUseCase.class)
                    .create(invoiceRequest));
        if(flags[1])
            fileNames.addAll(BeanFactory.getBean(CreateCMSClinicUseCase.class)
                    .create(invoiceRequest));
        if(flags[2])
            fileNames.addAll(BeanFactory.getBean(CreateCMSPatientCaseUseCase.class)
                    .create(invoiceRequest));
        if(flags[3])
            fileNames.addAll(BeanFactory.getBean(CreateCMSDatesUseCase.class)
                    .create(invoiceRequest));
        if(flags[4])
            fileNames.addAll(BeanFactory.getBean(CreateCMSAuthorizationUseCase.class)
                    .create(invoiceRequest));

        return fileNames;
    }
}
