package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FlagPatientAuthorizationUseCase {
    @Autowired
    PatientRepository patientRepository;

    public void turnOff(Long patientId) {
        patientRepository.turnOffAuthorization(patientId);
    }
    public void turnOn(Long patientId) {
        patientRepository.turnOnAuthorization(patientId);
    }
}
