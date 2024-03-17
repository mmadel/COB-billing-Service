package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import org.springframework.stereotype.Component;

@Component
public class SessionNoCreditAuthorizationValidation implements SessionAuthorizationValidation {
    private SessionAuthorizationValidation sessionAuthorizationValidation;
    @Override
    public void setNextHandler(SessionAuthorizationValidation nextValidation) {
        this.sessionAuthorizationValidation = nextValidation;
    }

    @Override
    public void processRequest(PatientSession patientSession) throws AuthorizationException {

    }
}
