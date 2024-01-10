package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.*;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.model.common.BasicAddress;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInsuranceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInsuranceInternalCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CreatePatientInsuranceCompanyUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientRepository repository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    PatientInsuranceInternalCompanyRepository patientInsuranceInternalCompanyRepository;
    @Autowired
    PatientInsuranceExternalCompanyRepository patientInsuranceExternalCompanyRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;

    public boolean create(PatientInsurance patientInsurance, Long patientId) {
        PatientInsuranceEntity toBeCreated = mapper.map(patientInsurance, PatientInsuranceEntity.class);
        PatientEntity patient = repository.findById(patientId).orElseThrow(() -> new IllegalArgumentException());
        toBeCreated.setPatient(patient);
        PatientInsuranceEntity created = patientInsuranceRepository.save(toBeCreated);
        switch (patientInsurance.getVisibility()) {
            case Internal:
                InsuranceCompanyEntity insuranceCompany = createInsuranceCompany(patientInsurance.getInsuranceCompany(), patientInsurance.getInsuranceCompanyAddress());
                mapPatientInsuranceToInsuranceCompany(created, insuranceCompany);
                break;
            case External:
                InsuranceCompanyExternalEntity insuranceCompanyExternal = createExternalInsuranceCompany(patientInsurance.getInsuranceCompany(), patientInsurance.getInsuranceCompanyAddress());
                mapPatientInsuranceToExternalInsuranceCompany(created, insuranceCompanyExternal);
                break;
        }
        return true;
    }

    private InsuranceCompanyEntity createInsuranceCompany(String[] insuranceCompany, BasicAddress insuranceCompanyNameAddress) {
        Optional<InsuranceCompanyEntity> optionalInsuranceCompany = insuranceCompanyRepository.findByInsuranceCompanyName(insuranceCompany[0]);
        if (optionalInsuranceCompany.isPresent()) {
            return optionalInsuranceCompany.get();
        } else {
            InsuranceCompanyEntity toBeCreated = new InsuranceCompanyEntity();
            toBeCreated.setName(insuranceCompany[0]);
            toBeCreated.setAddress(insuranceCompanyNameAddress);
            toBeCreated.setUuid(UUID.randomUUID().toString());
            InsuranceCompanyEntity created = insuranceCompanyRepository.save(toBeCreated);
            createInsuranceCompanyConfiguration(created);
            return created;
        }
    }

    private InsuranceCompanyExternalEntity createExternalInsuranceCompany(String[] insuranceCompany, BasicAddress insuranceCompanyNameAddress) {
        Optional<InsuranceCompanyExternalEntity> optionalInsuranceCompanyExternal = insuranceCompanyExternalRepository.findByInsuranceCompanyName(insuranceCompany[0]);
        if (optionalInsuranceCompanyExternal.isPresent()) {
            return optionalInsuranceCompanyExternal.get();
        } else {
            InsuranceCompanyExternalEntity toBeCreated = new InsuranceCompanyExternalEntity();
            toBeCreated.setName(insuranceCompany[0]);
            toBeCreated.setDisplayName(insuranceCompany[0]);
            toBeCreated.setPayerId(Long.parseLong(insuranceCompany[1]));
            toBeCreated.setAddress(insuranceCompanyNameAddress);
            InsuranceCompanyExternalEntity created = insuranceCompanyExternalRepository.save(toBeCreated);
            createInsuranceCompanyConfiguration(created);
            return created;
        }
    }

    private void mapPatientInsuranceToInsuranceCompany(PatientInsuranceEntity patientInsurance, InsuranceCompanyEntity insuranceCompany) {
        PatientInsuranceInternalCompanyEntity patientInsuranceInternalCompany = new PatientInsuranceInternalCompanyEntity();
        patientInsuranceInternalCompany.setPatientInsurance(patientInsurance);
        patientInsuranceInternalCompany.setInsuranceCompany(insuranceCompany);
        patientInsuranceInternalCompanyRepository.save(patientInsuranceInternalCompany);
    }

    private void mapPatientInsuranceToExternalInsuranceCompany(PatientInsuranceEntity patientInsurance, InsuranceCompanyExternalEntity insuranceCompany) {
        PatientInsuranceExternalCompanyEntity patientInsuranceExternalCompany = new PatientInsuranceExternalCompanyEntity();
        patientInsuranceExternalCompany.setExternalPatientInsurance(patientInsurance);
        patientInsuranceExternalCompany.setInsuranceCompany(insuranceCompany);
        patientInsuranceExternalCompanyRepository.save(patientInsuranceExternalCompany);
    }

    private void createInsuranceCompanyConfiguration(Object insuranceCompany) {
        InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration = new InsuranceCompanyConfigurationEntity();
        insuranceCompanyConfiguration.setBox32(false);
        insuranceCompanyConfiguration.setBox26("insured_primary_id");
        insuranceCompanyConfiguration.setBox33(-1L);
        if (insuranceCompany instanceof InsuranceCompanyEntity) {
            insuranceCompanyConfiguration.setInternalInsuranceCompany((InsuranceCompanyEntity) insuranceCompany);
            insuranceCompanyConfiguration.setExternalInsuranceCompany(null);
        }

        if (insuranceCompany instanceof InsuranceCompanyExternalEntity) {
            insuranceCompanyConfiguration.setExternalInsuranceCompany((InsuranceCompanyExternalEntity) insuranceCompany);
            insuranceCompanyConfiguration.setInternalInsuranceCompany(null);
        }
        insuranceCompanyConfigurationRepository.save(insuranceCompanyConfiguration);
    }
}
