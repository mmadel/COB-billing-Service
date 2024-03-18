package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.PickedAuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.validation.ExecuteSessionAuthorizationValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSessionSubmission {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ExecuteSessionAuthorizationValidation executeSessionAuthorizationValidation;
    @Autowired
    ModelMapper mapper;

    public void submit(SubmissionSession submissionSession) throws AuthorizationException {
        PatientSessionEntity session = patientSessionRepository.findById(submissionSession.getPatientSession().getId()).get();
        if (session.getPatientAuthorization() != null) {
            System.out.println("session attached, call validation COR");
            executeSessionAuthorizationValidation.execute(submissionSession, authorizationData(session.getPatientAuthorization()));
        } else {
            System.out.println("session not attached, call selection COR");
        }
    }

    private PickedAuthorizationSession authorizationData(PatientAuthorizationEntity patientAuthorization) {
        return PickedAuthorizationSession.builder()
                .startDate(patientAuthorization.getStartDateNumber())
                .expiryDate(patientAuthorization.getExpireDateNumber())
                .remainingValue(patientAuthorization.getRemaining())
                .insuranceCompanyId(patientAuthorization.getPatientInsuranceCompany())
                .build();
    }
}
