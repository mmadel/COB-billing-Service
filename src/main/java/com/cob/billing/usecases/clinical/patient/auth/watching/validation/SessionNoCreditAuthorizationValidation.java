package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.clinical.patient.session.PatientSession;
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
    public void processRequest(PatientSession patientSession, Long[] authorizationData) throws AuthorizationException {
        if (authorizationData[2] == 0)
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_NO_REMAINING, new Object[]{patientSession.getServiceDate().toString()});
        else
            sessionAuthorizationValidation.processRequest(patientSession, authorizationData);


    }
}
