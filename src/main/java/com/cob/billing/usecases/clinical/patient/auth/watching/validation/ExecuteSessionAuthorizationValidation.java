package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecuteSessionAuthorizationValidation {
    @Autowired
    SessionOutOfRangeAuthorizationValidation sessionOutOfRangeAuthorizationValidation;
    @Autowired
    SessionNoCreditAuthorizationValidation sessionNoCreditAuthorizationValidation;
    @Autowired
    SessionInvalidInsuranceCompanyValidation sessionInvalidInsuranceCompanyValidation;

    public void check(PatientSession patientSession, Long[] authorizationData) throws AuthorizationException {
        sessionOutOfRangeAuthorizationValidation.setNextValidation(sessionNoCreditAuthorizationValidation);
        sessionNoCreditAuthorizationValidation.setNextValidation(sessionInvalidInsuranceCompanyValidation);
        sessionOutOfRangeAuthorizationValidation.processRequest(patientSession, authorizationData);
    }
}
