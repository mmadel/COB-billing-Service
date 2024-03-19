package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.selection.SelectAuthorizationForNotAssignedSessionUseCase;
import com.cob.billing.usecases.clinical.patient.auth.watching.submission.SubmitMatchedSessionAuthorizationUseCase;
import com.cob.billing.usecases.clinical.patient.auth.watching.validation.ValidateSessionAuthorizationUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthorizationSelectionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ValidateSessionAuthorizationUseCase validateSessionAuthorizationUseCase;
    @Autowired
    SubmitMatchedSessionAuthorizationUseCase submitMatchedSessionAuthorizationUseCase;
    @Autowired
    SelectAuthorizationForNotAssignedSessionUseCase selectAuthorizationForNotAssignedSessionUseCase;
    @Autowired
    ModelMapper mapper;

    public void select(SubmissionSession submissionSession) throws AuthorizationException {
        PatientSessionEntity session = patientSessionRepository.findById(3L).get();
        if (session.getPatientAuthorization() != null) {
            AuthorizationSession authorizationSession = authorizationData(session.getPatientAuthorization());
            validateSessionAuthorizationUseCase.validate(submissionSession, authorizationSession);
            submitMatchedSessionAuthorizationUseCase.submit(submissionSession,  authorizationSession.getId(), authorizationSession.getAuthorizationNumber());
        } else {
            System.out.println("session not attached, call selection COR");
            selectAuthorizationForNotAssignedSessionUseCase.select(submissionSession);
        }
    }

    private AuthorizationSession authorizationData(PatientAuthorizationEntity patientAuthorization) {
        return AuthorizationSession.builder()
                .id(patientAuthorization.getId())
                .startDate(patientAuthorization.getStartDateNumber())
                .expiryDate(patientAuthorization.getExpireDateNumber())
                .remainingValue(patientAuthorization.getRemaining())
                .insuranceCompanyId(patientAuthorization.getPatientInsuranceCompany())
                .authorizationNumber(patientAuthorization.getAuthNumber())
                .build();
    }
}
