package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Qualifier("RemainingHandling")
public class AuthorizationRemainingHandling implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) throws AuthorizationException {
        if (!(request.getPatientInformation().getAuthorizationSelection().getRemainingCounter() >= 1)) {
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.AUTH_CREDIT_REMAINING,new Object[]{""});
        }

    }
}
