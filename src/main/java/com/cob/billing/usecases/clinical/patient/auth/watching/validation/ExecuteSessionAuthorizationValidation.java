package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
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

    public void check(SubmissionSession submissionSession, Long[] authorizationData) throws AuthorizationException {
        sessionOutOfRangeAuthorizationValidation.setNextValidation(sessionNoCreditAuthorizationValidation);
        sessionNoCreditAuthorizationValidation.setNextValidation(sessionInvalidInsuranceCompanyValidation);
        sessionOutOfRangeAuthorizationValidation.processRequest(submissionSession, authorizationData);
    }
}
