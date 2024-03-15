package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.stereotype.Component;

@Component
public class PatientAuthorizationCheckerUseCase {


    public void check(InvoiceRequest invoiceRequest) throws AuthorizationException {

    }
}
