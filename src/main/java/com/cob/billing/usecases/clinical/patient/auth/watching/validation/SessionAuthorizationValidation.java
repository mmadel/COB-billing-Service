package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.clinical.patient.session.PatientSession;

public interface SessionAuthorizationValidation {
    void setNextValidation(SessionAuthorizationValidation nextValidation);

    void processRequest(PatientSession patientSession , Long[] authorizationData) throws AuthorizationException;
}
