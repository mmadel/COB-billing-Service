package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientRepository repository;
    public Long create(Patient patient) {
        PatientEntity toBeCreated = mapper.map(patient , PatientEntity.class);
        return repository.save(toBeCreated).getId();
    }
}
