package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class SelectPatientAuthorizationUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public void select(Long sessionId, Long authorizationId) {
        patientSessionRepository.assignAuthorization(sessionId, authorizationId);
    }
}
