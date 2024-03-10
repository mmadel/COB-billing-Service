package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("OverLapSelectionHandler")
public class AuthorizationOverLapSelectionHandler implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        if (isAuthorizationsOverLapped(request.getPatientInformation().getAuthorizationSelection().getAuthorizations()))
            System.out.println("//throw exception : the patient authorizations is overlapped: Please Select authorization");
        else
            nextAuthorizationHandling.processRequest(request);
    }

    private Boolean isAuthorizationsOverLapped(List<Long[]> authorizations) {
        return true;
    }
}
