package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientCaseRepository;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.ReferringProviderRepository;
import com.cob.billing.util.ListUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    PayerRepository payerRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;

    @Transactional
    public Long create(Patient patient) {
        if (patient.getId() != null) {
            PatientEntity originalPatient = repository.findById(patient.getId()).get();
            deleteCase(originalPatient, patient.getCases());
            deleteInsurance(originalPatient, patient.getPatientInsurances());
        }

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

    private void deleteCase(PatientEntity patient, List<PatientCase> cases) {
        List<Long> originalIds = patient.getCases()
                .stream()
                .map(PatientCaseEntity::getId)
                .collect(Collectors.toList());
        if (cases == null)
            cases = new ArrayList<>();
        List<Long> persistedSubmittedIds = cases
                .stream()
                .map(PatientCase::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> toBeDeleted = ListUtils.returnDifference(originalIds, persistedSubmittedIds);
        if (toBeDeleted.size() > 0)
            patientCaseRepository.deleteAllById(toBeDeleted);
    }

    private void deleteInsurance(PatientEntity patient, List<PatientInsurance> insurances) {
        List<Long> originalIds = patient.getInsurances()
                .stream()
                .map(PatientInsuranceEntity::getId)
                .collect(Collectors.toList());
        if (insurances == null)
            insurances = new ArrayList<>();
        List<Long> persistedSubmittedIds = insurances
                .stream()
                .map(PatientInsurance::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> toBeDeleted = ListUtils.returnDifference(originalIds, persistedSubmittedIds);
        if (toBeDeleted.size() > 0)
            patientInsuranceRepository.deleteAllById(toBeDeleted);
    }

    private void assignReferringProvider(PatientEntity patient, String npi) {
        ReferringProviderEntity referringProvider = referringProviderRepository.findByNpi(npi).orElseThrow(() -> new IllegalArgumentException("referring provider not found"));
        patient.setReferringProvider(referringProvider);
        repository.save(patient);
    }

    private void createPatientInsurances(PatientEntity patient, List<PatientInsurance> insurances) {
        List<PatientInsuranceEntity> list = insurances.stream()
                .map(patientInsurance -> {
                    patientInsurance.getPatientInsurancePolicy();
                    PatientInsuranceEntity toBeCreated = mapper.map(patientInsurance, PatientInsuranceEntity.class);
//                    if (toBeCreated.getPayer() != null && toBeCreated.getPayer().getPayerId() == null) {
//                        InsuranceCompanyEntity created = createInsuranceCompany(toBeCreated.getPayer());
//                        toBeCreated.setInsuranceCompany(created);
//                        toBeCreated.setPayer(null);
//                        createInsuranceCompanyConfiguration(created.getId());
//                    } else {
//                        createInsuranceCompanyConfiguration(toBeCreated.getPayer().getPayerId());
//                    }
                    toBeCreated.setPatient(patient);
                    return toBeCreated;
                }).collect(Collectors.toList());
        List<PatientInsuranceEntity> createdList = patientInsuranceRepository.saveAll(list);
        patient.setInsurances(createdList);
    }

    private InsuranceCompanyEntity createInsuranceCompany(Payer payer) {
        InsuranceCompanyEntity entity = new InsuranceCompanyEntity();
        entity.setName(payer.getName());
        entity.setAddress(payer.getAddress());
        InsuranceCompanyEntity created = insuranceCompanyRepository.save(entity);
        return created;
    }

    private void createInsuranceCompanyConfiguration(Long insuranceCompanyId) {
        InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration = new InsuranceCompanyConfigurationEntity();
        insuranceCompanyConfiguration.setBox32(false);
        insuranceCompanyConfiguration.setBox26("insured_primary_id");
       // insuranceCompanyConfiguration.setInsuranceCompanyIdentifier(insuranceCompanyId);
        insuranceCompanyConfiguration.setBox33(-1L);
        insuranceCompanyConfigurationRepository.save(insuranceCompanyConfiguration);
    }
}
