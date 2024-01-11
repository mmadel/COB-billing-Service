package com.cob.billing.usecases.clinical.patient.cases;

import com.cob.billing.repositories.clinical.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientCaseUseCase {
    @Autowired
    PatientCaseRepository patientCaseRepository;

    public boolean delete(Long id) {
        patientCaseRepository.deleteById(id);
        return true;
    }
}
