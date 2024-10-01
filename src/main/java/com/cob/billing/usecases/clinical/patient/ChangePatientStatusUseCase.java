package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChangePatientStatusUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Transactional
    public void changeStatus(Long patientId, Boolean status) {
        patientRepository.changePatientStatus(status, patientId);
    }
}
