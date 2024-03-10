package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

import java.lang.reflect.Array;
import java.util.List;

public class AuthorizationSelectionHandling implements AuthorizationHandling{
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        Long[] selectedAuthorizationDates = new Long[2];
        List<Long[]> dates = request.getPatientInformation().getAuthorizationInformation().getMetaData();
        if (dates.size() == 1)
            System.out.println("Single Authorization");
        else
            System.out.println("Multiple Authorizations");

        int hasAuthorizationDates = Array.getLength(selectedAuthorizationDates);
        if(hasAuthorizationDates > 0){
            nextAuthorizationHandling.processRequest(request);
        }else {
            // throw exception if over-lap
            // fill to submit claim with auth number
        }
    }
}
