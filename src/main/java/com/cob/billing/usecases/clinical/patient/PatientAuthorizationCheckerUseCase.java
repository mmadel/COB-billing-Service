package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.clinical.patient.auth.HandlePatientAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAuthorizationCheckerUseCase {
    @Autowired
    HandlePatientAuthorizationUseCase handlePatientAuthorizationUseCase;

    public void check(InvoiceRequest invoiceRequest) throws AuthorizationException {
        handlePatientAuthorizationUseCase.handle(invoiceRequest);
    }
}
