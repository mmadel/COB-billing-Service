package com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;

public class InsuranceCompanyValidator extends AuthorizationValidator {
    @Override
    public boolean validate(SubmissionSession submissionSession) throws AuthorizationException {
        if (!submissionSession.getInsuranceCompanyId().equals(submissionSession.getAuthorizationSession().getInsuranceCompanyId()))
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_INVALID_INSURANCE_COMPANY, new Object[]{submissionSession.getDateOfService().toString()});
        else
            return validateNext(submissionSession);
    }
}
