package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Qualifier("MultipleSessionSelectionHandler")
public class AuthorizationMultipleSessionSelectionHandler implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        if (hasMultipleSessions(request.getPatientInformation().getAuthorizationSelection().getAuthorizations()))
            System.out.println("//throw exception : Submitted service lines attached to multiple sessions : Please Select authorization");
        else
            nextAuthorizationHandling.processRequest(request);

    }

    private Boolean hasMultipleSessions(List<Long[]> authorizations) {
        Set<Long> seenSecondElements = new HashSet<>();

        for (Long[] array : authorizations) {
            Long second = array[3];
            if (seenSecondElements.contains(second)) {
                return false;
            }
            seenSecondElements.add(second);
        }
        return true;
    }
}
