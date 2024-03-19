package com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;

public abstract class AuthorizationValidator {
    private AuthorizationValidator next;

    public static AuthorizationValidator register(AuthorizationValidator first, AuthorizationValidator... chain) {
        AuthorizationValidator head = first;
        for (AuthorizationValidator nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean validate(SubmissionSession submissionSession) throws AuthorizationException;

    protected boolean validateNext(SubmissionSession submissionSession) throws AuthorizationException {
        if (next == null) {
            return true;
        }
        return next.validate(submissionSession);
    }
}
