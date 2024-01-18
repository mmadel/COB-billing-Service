package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.entity.clinical.provider.ProviderEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.model.clinical.patient.insurance.PatientInsuranceAdvanced;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurancePolicy;
import com.cob.billing.model.clinical.patient.insurance.PatientRelation;
import com.cob.billing.model.clinical.provider.Provider;
import com.cob.billing.model.response.PatientResponse;
import com.cob.billing.model.response.ProviderResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientUseCase {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MapPatientUseCase mapPatientUseCase;

    public PatientResponse findAll(Pageable paging) {
        Page<PatientEntity> pages = patientRepository.findAll(paging);
        long total = (pages).getTotalElements();
        List<Patient> records = pages.stream().map(patient -> mapPatientUseCase.map(patient))
                .collect(Collectors.toList());
        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    public Patient findById(Long patientId) {
        PatientEntity entity = patientRepository.findById(patientId).get();
        return mapPatientUseCase.map(entity);
    }
}
