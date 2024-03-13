package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.clinical.patient.auth.claim.approval.AuthorizationHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PatientAuthorizationCheckerUseCase {
    @Autowired
    @Qualifier("TurningHandling")
    AuthorizationHandling authorizationTurningHandling;

    @Autowired
    @Qualifier("SelectionHandling")
    AuthorizationHandling authorizationSelectionHandling;

    @Autowired
    @Qualifier("MultipleSessionSelectionHandler")
    AuthorizationHandling authorizationMultipleSessionSelectionHandler;

    @Autowired
    @Qualifier("OverLapSelectionHandler")
    AuthorizationHandling authorizationOverLapSelectionHandler;

    @Autowired
    @Qualifier("ExpirationHandling")
    AuthorizationHandling authorizationExpirationHandling;

    @Autowired
    @Qualifier("AssignHandler")
    AuthorizationHandling authorizationAssignHandler;


    @Autowired
    @Qualifier("RemainingHandling")
    AuthorizationHandling authorizationRemainingHandling;

    public void check(InvoiceRequest invoiceRequest) throws AuthorizationException {
        authorizationTurningHandling.setNextHandler(authorizationSelectionHandling);
        authorizationSelectionHandling.setNextHandler(authorizationMultipleSessionSelectionHandler);
        authorizationMultipleSessionSelectionHandler.setNextHandler(authorizationOverLapSelectionHandler);
        authorizationOverLapSelectionHandler.setNextHandler(authorizationAssignHandler);
        authorizationAssignHandler.setNextHandler(authorizationExpirationHandling);
        authorizationExpirationHandling.setNextHandler(authorizationRemainingHandling);
        authorizationTurningHandling.processRequest(invoiceRequest);
    }
}
