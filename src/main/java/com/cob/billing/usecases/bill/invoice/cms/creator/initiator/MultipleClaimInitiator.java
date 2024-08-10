package com.cob.billing.usecases.bill.invoice.cms.creator.initiator;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.models.MultipleItemsWrapper;
import com.cob.billing.usecases.bill.invoice.cms.creator.multiple.CreateMultipleCMSClaimsUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.single.CreateCMSClaimUseCase;
import com.cob.billing.util.BeanFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MultipleClaimInitiator extends ClaimInitiator {
    Boolean[] flags;
    public MultipleClaimInitiator(Boolean[] flags) {
        super();
        this.flags = flags;
    }
    @Override
    public List<String> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        CreateMultipleCMSClaimsUseCase createMultipleCMSClaimsUseCase = BeanFactory.getBean(CreateMultipleCMSClaimsUseCase.class);
        return createMultipleCMSClaimsUseCase.create(invoiceRequest,flags);
    }
}
