package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.MinimalPatient;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.response.MinimalPatientResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientUseCase {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MapPatientUseCase mapPatientUseCase;
    @Autowired
    AssignPatientAuthorizationUseCase assignPatientAuthorizationUseCase;

    public MinimalPatientResponse findAll(Pageable paging) {
        Page<PatientEntity> pages = patientRepository.findAll(paging);
        long total = (pages).getTotalElements();
        List<MinimalPatient> records = pages.stream()
                .filter(patient -> patient.isStatus())
                .map(patient -> {
                    MinimalPatient minimalPatient = new MinimalPatient();
                    minimalPatient.setId(patient.getId());
                    minimalPatient.setName(patient.getLastName() + ',' + patient.getFirstName());
                    minimalPatient.setDateOfBirth(patient.getBirthDate());
                    minimalPatient.setEmail(patient.getEmail());
                    return minimalPatient;
                })
                .collect(Collectors.toList());
        return MinimalPatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    public Patient findById(Long patientId) {
        PatientEntity entity = patientRepository.findById(patientId).get();
        Patient patient =  mapPatientUseCase.map(entity);
        //assignPatientAuthorizationUseCase.find(patient);
        return patient;
    }
}
