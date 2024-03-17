package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
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
    public void processRequest(SubmissionSession submissionSession, Long[] authorizationData) throws AuthorizationException {
        if (submissionSession.getPatientSession().getServiceDate() > authorizationData[0] && submissionSession.getPatientSession().getServiceDate() < authorizationData[1])
            sessionAuthorizationValidation.processRequest(submissionSession, authorizationData);
        else
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});

    }
}
