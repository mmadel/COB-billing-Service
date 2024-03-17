package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
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
    public void processRequest(PatientSession patientSession, Long[] authorizationData) throws AuthorizationException {
        if (patientSession.getServiceDate() > authorizationData[0] && patientSession.getServiceDate() < authorizationData[1])
            sessionAuthorizationValidation.processRequest(patientSession, authorizationData);
        else
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{patientSession.getServiceDate().toString()});

    }
}
