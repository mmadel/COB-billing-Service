package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.AuthorizationValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.InsuranceCompanyValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.OutOfCreditValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.OutOfRangeValidator;

public class EagerSessionAuthorizationExecutor extends SessionAuthorizationExecutor {
    @Override
    public void execute(SubmissionSession submissionSession) throws AuthorizationException {
        AuthorizationValidator validator = AuthorizationValidator.register(
                new OutOfRangeValidator(),
                new OutOfCreditValidator(),
                new InsuranceCompanyValidator()
        );
        validator.validate(submissionSession);
        deduct(submissionSession);
    }
}
