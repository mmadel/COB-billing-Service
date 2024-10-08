package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.MinimalPatient;
import com.cob.billing.model.clinical.patient.PatientSearchCriteria;
import com.cob.billing.model.response.MinimalPatientResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindFilteredPatientUseCase {
    @Autowired
    PatientRepository patientRepository;

    public MinimalPatientResponse find(Pageable paging, PatientSearchCriteria searchCriteria) {
        Page<PatientEntity> pages = patientRepository.findFilter(paging, searchCriteria.getName() != null ? searchCriteria.getName().toUpperCase().trim() : null,
                searchCriteria.getPhone() != null ? searchCriteria.getPhone().trim() : null,
                searchCriteria.getEmail() != null ? searchCriteria.getEmail().trim() : null,
                searchCriteria.getInsuranceCompany() != null ? searchCriteria.getInsuranceCompany().toUpperCase().trim() : null);
        long total = (pages).getTotalElements();
        List<MinimalPatient> records = pages.stream().map(patient -> {
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
}
