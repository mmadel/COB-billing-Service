package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.AuthorizationValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.OutOfCreditValidator;

public class ExecuteOrphanSessionValidationUseCase {
    public void execute(SubmissionSession submissionSession, AuthorizationSession authorizationSession) throws AuthorizationException {
        AuthorizationValidator validator = AuthorizationValidator.register(
                new OutOfCreditValidator()
        );
        validator.validate(submissionSession);
    }
}
