package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSelection {
    private SessionAuthorizationExecutor sessionAuthorizationExecutor;

    public void select(SubmissionSession submissionSession) throws AuthorizationException {
        pickExecutor(submissionSession.getAuthorizationSession());
        sessionAuthorizationExecutor.execute(submissionSession);
    }
    private void pickExecutor(AuthorizationSession authorizationSession) {
        if (authorizationSession != null)
            sessionAuthorizationExecutor = new EagerSessionAuthorizationExecutor();
        else
            sessionAuthorizationExecutor = new LazySessionAuthorizationExecutor();
    }
}
