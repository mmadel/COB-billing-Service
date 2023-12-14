package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.model.bill.invoice.InvoiceRequestCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateInvoiceUseCase {
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;

    public void create(InvoiceRequestCreation invoiceRequestCreation) {
        generateCMSDocument();
        invoiceRequestCreation.getServiceCodeIds().stream()
                .forEach(serviceCodeId -> {
                    changeSessionStatus(serviceCodeId);
                });
    }

    /*TODO
        generate CMS-1500 document , may be using lib or generate it from scratch
     */
    private void generateCMSDocument() {

    }

    private void changeSessionStatus(Long serviceCodeId) {
        changeSessionStatusUseCase.change(serviceCodeId);
    }
}
