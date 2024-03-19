package com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.http.HttpStatus;

public class OutOfCreditValidator extends AuthorizationValidator {
    @Override
    public boolean validate(SubmissionSession submissionSession) throws AuthorizationException {
        if (submissionSession.getAuthorizationSession().getRemainingValue() == 0)
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_NO_REMAINING, new Object[]{submissionSession.getDateOfService().toString()});
        else
            return validateNext(submissionSession);
    }
}
