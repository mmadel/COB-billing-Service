package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.clinical.patient.auth.watching.AuthorizationWatching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAuthorizationCheckerUseCase {
    @Autowired
    AuthorizationWatching authorizationWatching;

    public void check(InvoiceRequest invoiceRequest) throws AuthorizationException {
        authorizationWatching.watch(invoiceRequest);
    }
}
