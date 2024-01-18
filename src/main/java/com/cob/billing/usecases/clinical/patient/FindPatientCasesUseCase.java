package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientCasesUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientCase> find(Long patientId) {
        List<PatientCaseEntity> patientCaseEntities = patientRepository.findPatientCases(patientId);
        return patientCaseEntities.stream()
                .map(patientCaseEntity -> mapper.map(patientCaseEntity, PatientCase.class))
                .collect(Collectors.toList());
    }

}
