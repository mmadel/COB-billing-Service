package com.cob.billing.usecases.clinical.patient.auth.watching.validator;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionOutOfRangeAuthorizationValidation implements SessionAuthorizationValidation {
    private SessionAuthorizationValidation sessionAuthorizationValidation;

    @Override
    public void setNextValidation(SessionAuthorizationValidation nextValidation) {
        this.sessionAuthorizationValidation = nextValidation;
    }

    @Override
    public void processRequest(SubmissionSession submissionSession, AuthorizationSession authorizationSession) throws AuthorizationException {
        if (submissionSession.getDateOfService() >= authorizationSession.getStartDate() && submissionSession.getDateOfService() <= authorizationSession.getExpiryDate())
            sessionAuthorizationValidation.processRequest(submissionSession, authorizationSession);
        else
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getDateOfService().toString()});

    }
}
