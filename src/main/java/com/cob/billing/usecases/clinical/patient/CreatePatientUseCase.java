package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.repositories.clinical.PatientCaseRepository;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.ReferringProviderRepository;
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
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;

    public Long create(Patient patient) {
        PatientEntity toBeCreated = mapper.map(patient, PatientEntity.class);
        toBeCreated.setReferringProvider(null);
        PatientEntity created = repository.save(toBeCreated);
        if (patient.getCases() != null && !patient.getCases().isEmpty())
            createPatientClinics(created, patient.getCases());
        if (patient.getReferringProvider() != null)
            assignReferringProvider(created, patient.getReferringProvider().getNpi());
        if (patient.getPatientInsurances() != null && !patient.getPatientInsurances().isEmpty())
            createPatientInsurances(created, patient.getPatientInsurances());
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

    public void assignReferringProvider(PatientEntity patient, String npi) {
        ReferringProviderEntity referringProvider = referringProviderRepository.findByNpi(npi).orElseThrow(() -> new IllegalArgumentException("referring provider not found"));
        patient.setReferringProvider(referringProvider);
        repository.save(patient);
    }

    public void createPatientInsurances(PatientEntity patient, List<PatientInsurance> insurances) {
        List<PatientInsuranceEntity> list = insurances.stream()
                .map(patientInsurance -> {
                    PatientInsuranceEntity toBeCreated = mapper.map(patientInsurance, PatientInsuranceEntity.class);
                    toBeCreated.setPatient(patient);
                    return toBeCreated;
                }).collect(Collectors.toList());
        patientInsuranceRepository.saveAll(list);
    }
}
