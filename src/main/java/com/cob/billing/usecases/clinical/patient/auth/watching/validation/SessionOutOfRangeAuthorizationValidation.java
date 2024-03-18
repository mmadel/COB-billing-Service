package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.PickedAuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.model.clinical.patient.session.PatientSession;
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
    public void processRequest(SubmissionSession submissionSession, PickedAuthorizationSession pickedAuthorizationSession) throws AuthorizationException {
        if (submissionSession.getPatientSession().getServiceDate() > pickedAuthorizationSession.getStartDate() && submissionSession.getPatientSession().getServiceDate() < pickedAuthorizationSession.getExpiryDate())
            sessionAuthorizationValidation.processRequest(submissionSession, pickedAuthorizationSession);
        else
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});

    }
}
