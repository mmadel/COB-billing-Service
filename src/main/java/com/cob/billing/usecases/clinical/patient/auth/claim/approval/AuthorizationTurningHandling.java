package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

public class AuthorizationTurningHandling implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        if (!request.getPatientInformation().getAuthorizationInformation().getTurning()) {
            System.out.println("Authorization is turned Off");
        } else {
            nextAuthorizationHandling.processRequest(request);
        }

    }
}
