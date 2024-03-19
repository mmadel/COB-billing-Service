package com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;

public class OutOfRangeValidator extends AuthorizationValidator {
    @Override
    public boolean validate(SubmissionSession submissionSession) throws AuthorizationException {
        if (submissionSession.getDateOfService() >= submissionSession.getAuthorizationSession().getStartDate() && submissionSession.getDateOfService() <= submissionSession.getAuthorizationSession().getExpiryDate())
            return validateNext(submissionSession);
        else
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getDateOfService().toString()});
    }
}
