package com.cob.billing.usecases.clinical.patient.cases;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.repositories.clinical.PatientCaseRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientCaseUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientRepository repository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    public Long create(PatientCase model, Long patientId) {
        PatientEntity patient = repository.findById(patientId).orElseThrow(() -> new IllegalArgumentException());
        PatientCaseEntity toBeCreated = mapper.map(model, PatientCaseEntity.class);
        toBeCreated.setPatient(patient);
        return patientCaseRepository.save(toBeCreated).getId();

    }
}
