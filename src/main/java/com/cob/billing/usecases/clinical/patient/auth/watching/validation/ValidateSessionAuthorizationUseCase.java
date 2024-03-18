package com.cob.billing.usecases.clinical.patient.auth.watching.validation;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.PickedAuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateSessionAuthorizationUseCase {
    @Autowired
    SessionOutOfRangeAuthorizationValidation sessionOutOfRangeAuthorizationValidation;
    @Autowired
    SessionNoCreditAuthorizationValidation sessionNoCreditAuthorizationValidation;
    @Autowired
    SessionInvalidInsuranceCompanyValidation sessionInvalidInsuranceCompanyValidation;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void validate(SubmissionSession submissionSession, PickedAuthorizationSession pickedAuthorizationSession) throws AuthorizationException {
        sessionOutOfRangeAuthorizationValidation.setNextValidation(sessionNoCreditAuthorizationValidation);
        sessionNoCreditAuthorizationValidation.setNextValidation(sessionInvalidInsuranceCompanyValidation);
        sessionOutOfRangeAuthorizationValidation.processRequest(submissionSession, pickedAuthorizationSession);
    }

}
