package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;

public abstract class SessionAuthorizationExecutor {
    abstract void execute(SubmissionSession submissionSession) throws AuthorizationException;

    protected void deduct(SubmissionSession submissionSession) {
        submissionSession.getAuthorizationSession().setRemainingValue(submissionSession.getAuthorizationSession().getRemainingValue() - 1);
    }
}
