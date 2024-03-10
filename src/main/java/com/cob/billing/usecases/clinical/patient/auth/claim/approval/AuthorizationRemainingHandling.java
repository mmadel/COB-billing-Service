package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

public class AuthorizationRemainingHandling implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        if (request.getPatientInformation().getAuthorizationSelection().getRemainingCounter() > 1) {
            // Fill to Submit Claim
        } else {
            // throw exception
        }

    }
}
