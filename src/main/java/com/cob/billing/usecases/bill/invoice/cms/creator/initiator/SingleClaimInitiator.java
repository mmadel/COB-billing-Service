package com.cob.billing.usecases.bill.invoice.cms.creator.initiator;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.single.CreateCMSClaimUseCase;
import com.cob.billing.util.BeanFactory;

import java.io.IOException;
import java.util.List;

public class SingleClaimInitiator extends ClaimInitiator {

    @Override
    public List<String> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        CreateCMSClaimUseCase createCMSClaimUseCase = BeanFactory.getBean(CreateCMSClaimUseCase.class);
        return createCMSClaimUseCase.create(invoiceRequest);
    }
}
