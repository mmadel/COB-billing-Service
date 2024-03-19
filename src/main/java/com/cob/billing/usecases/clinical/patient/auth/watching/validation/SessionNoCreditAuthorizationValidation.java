package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionNoCreditAuthorizationValidation implements SessionAuthorizationValidation {
    private SessionAuthorizationValidation sessionAuthorizationValidation;

    @Override
    public void setNextValidation(SessionAuthorizationValidation nextValidation) {
        this.sessionAuthorizationValidation = nextValidation;
    }

    @Override
    public void processRequest(SubmissionSession submissionSession, AuthorizationSession authorizationSession) throws AuthorizationException {
        if (authorizationSession.getRemainingValue() == 0)
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_NO_REMAINING, new Object[]{""});
        else
            sessionAuthorizationValidation.processRequest(submissionSession, authorizationSession);


    }
}
