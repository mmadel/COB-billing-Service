package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;

public interface SessionAuthorizationValidation {
    void setNextValidation(SessionAuthorizationValidation nextValidation);

    void processRequest(SubmissionSession submissionSession , AuthorizationSession authorizationSession) throws AuthorizationException;
}
