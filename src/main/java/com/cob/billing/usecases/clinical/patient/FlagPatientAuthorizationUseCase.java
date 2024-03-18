package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FlagPatientAuthorizationUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public void turnOff(Long patientId) {
        patientRepository.turnOffAuthorization(patientId);
        removeSelectedAuthorization(patientId);
    }

    public void turnOn(Long patientId) {
        patientRepository.turnOnAuthorization(patientId);
    }

    private void removeSelectedAuthorization(Long patientId) {
        patientSessionRepository.unAssignAuthorization(patientId);
    }
}
