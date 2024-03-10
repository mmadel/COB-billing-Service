package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@Qualifier("ExpirationHandling")
public class AuthorizationExpirationHandling implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
//        Long expirationDate = request.getPatientInformation().getAuthorizationSelection().getExpiryDate();
//        if (expirationDate < new Date().getTime()) {
//            nextAuthorizationHandling.processRequest(request);
//        } else {
//            //throw exception
//        }
    }
}
