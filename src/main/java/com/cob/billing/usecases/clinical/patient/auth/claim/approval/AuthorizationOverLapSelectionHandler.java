package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
    public void processRequest(InvoiceRequest request) throws AuthorizationException {
        if (!request.getPatientInformation().getAuthorizationInformation().getSelected())
            if (isAuthorizationsOverLapped(request.getPatientInformation().getAuthorizationSelection().getAuthorizations()))
                throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.AUTH_OVERLAP, new Object[]{""});
            else {
                nextAuthorizationHandling.processRequest(request);
            }

    }

    private Boolean isAuthorizationsOverLapped(List<Long[]> authorizations) {
        /*
            [0] start date
            [1] expiry date
         */
        for (int i = 0; i < authorizations.size(); i++) {
            Long[] range1 = authorizations.get(i);
            Long startDate1 = range1[0];
            Long endDate1 = range1[1];
            for (int j = i + 1; j < authorizations.size(); j++) {
                Long[] range2 = authorizations.get(j);
                Long startDate2 = range2[0];
                Long endDate2 = range2[1];

                if (startDate1 <= endDate2 && startDate2 <= endDate1) {
                    // Ranges overlap
                    return true;
                }
            }
        }
        return false;
    }
}
