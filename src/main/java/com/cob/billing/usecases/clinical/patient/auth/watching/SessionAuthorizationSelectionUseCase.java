package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.PickedAuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
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
    ModelMapper mapper;

    public void select(SubmissionSession submissionSession) throws AuthorizationException {
        PatientSessionEntity session = patientSessionRepository.findById(submissionSession.getPatientSession().getId()).get();
        if (session.getPatientAuthorization() != null) {
            PickedAuthorizationSession pickedAuthorizationSession = authorizationData(session.getPatientAuthorization());
            validateSessionAuthorizationUseCase.validate(submissionSession, pickedAuthorizationSession);
            submitMatchedSessionAuthorizationUseCase.submit(submissionSession,  pickedAuthorizationSession.getId(),pickedAuthorizationSession.getAuthorizationNumber());
        } else {
            System.out.println("session not attached, call selection COR");
        }
    }

    private PickedAuthorizationSession authorizationData(PatientAuthorizationEntity patientAuthorization) {
        return PickedAuthorizationSession.builder()
                .id(patientAuthorization.getId())
                .startDate(patientAuthorization.getStartDateNumber())
                .expiryDate(patientAuthorization.getExpireDateNumber())
                .remainingValue(patientAuthorization.getRemaining())
                .insuranceCompanyId(patientAuthorization.getPatientInsuranceCompany())
                .authorizationNumber(patientAuthorization.getAuthNumber())
                .build();
    }
}
