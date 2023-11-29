package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.repositories.clinical.PatientCaseRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreatePatientUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientRepository repository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    public Long create(Patient patient) {
        PatientEntity created = repository.save(mapper.map(patient, PatientEntity.class));
        if (patient.getCases() != null || !patient.getCases().isEmpty())
            createPatientClinics(created, patient.getCases());
        return created.getId();
    }

    private void createPatientClinics(PatientEntity patient, List<PatientCase> cases) {
        List<PatientCaseEntity> list = cases.stream()
                .map(patientCase -> {
                    PatientCaseEntity toBeCreated = mapper.map(patientCase, PatientCaseEntity.class);
                    toBeCreated.setPatient(patient);
                    return toBeCreated;
                }).collect(Collectors.toList());
        patientCaseRepository.saveAll(list);
    }
}
