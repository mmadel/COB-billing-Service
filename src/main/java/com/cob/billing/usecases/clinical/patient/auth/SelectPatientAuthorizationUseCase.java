package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SelectPatientAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void select(Long patientId, Long authorizationId) {
        patientAuthorizationRepository.assignAuthorizationToPatient(patientId, authorizationId);
    }
}
