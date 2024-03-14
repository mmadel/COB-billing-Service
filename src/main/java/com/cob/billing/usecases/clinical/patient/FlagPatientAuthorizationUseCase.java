package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class FlagPatientAuthorizationUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void turnOff(Long patientId) {
        patientRepository.turnOffAuthorization(patientId);
        removeSelectedAuthorization(patientId);
    }

    public void turnOn(Long patientId) {
        patientRepository.turnOnAuthorization(patientId);
    }

    private void removeSelectedAuthorization(Long patientId) {
        List<PatientAuthorizationEntity> patientAuthorizationEntities = patientAuthorizationRepository.findByPatient_Id(patientId).get();
        //patientAuthorizationEntities.stream()
                //.filter(patientAuthorizationEntity -> patientAuthorizationEntity.getSelected())
                //.forEach(patientAuthorizationEntity -> patientAuthorizationEntity.setSelected(false));
        patientAuthorizationRepository.saveAll(patientAuthorizationEntities);
    }
}
