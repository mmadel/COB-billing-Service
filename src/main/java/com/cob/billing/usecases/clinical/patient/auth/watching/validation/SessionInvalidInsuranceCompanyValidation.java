package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.PickedAuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionInvalidInsuranceCompanyValidation implements SessionAuthorizationValidation {
    private SessionAuthorizationValidation sessionAuthorizationValidation;

    @Override
    public void setNextValidation(SessionAuthorizationValidation nextValidation) {
        this.sessionAuthorizationValidation = nextValidation;
    }

    @Override
    public void processRequest(SubmissionSession submissionSession, PickedAuthorizationSession pickedAuthorizationSession) throws AuthorizationException {
        if(!submissionSession.getInsuranceCompanyId().equals(pickedAuthorizationSession.getInsuranceCompanyId()))
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_INVALID_INSURANCE_COMPANY, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});


    }
}
