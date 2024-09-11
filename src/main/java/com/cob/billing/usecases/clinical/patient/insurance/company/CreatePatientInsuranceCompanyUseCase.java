package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.*;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.model.common.BasicAddress;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    InsuranceCompanyPayerRepository insuranceCompanyPayerRepository;

    @Transactional
    public PatientInsurance create(PatientInsurance patientInsurance, Long patientId) {
        PatientInsuranceEntity toBeCreated = mapper.map(patientInsurance, PatientInsuranceEntity.class);
        PatientEntity patient = repository.findById(patientId).orElseThrow(() -> new IllegalArgumentException());
        toBeCreated.setPatient(patient);
        if (patientInsurance.getId() == null)
            toBeCreated.setCreatedAt(new Date().getTime());
        PatientInsuranceEntity created = patientInsuranceRepository.save(toBeCreated);
        PatientInsurance result = new PatientInsurance();
        result.setId(created.getId());
        String[] insuranceCompanyValue;
        switch (patientInsurance.getVisibility()) {
            case Internal:
                InsuranceCompanyEntity insuranceCompany = createInsuranceCompany(patientInsurance.getInsuranceCompany(), patientInsurance.getInsuranceCompanyAddress());
                result.setAssigner(getAssigner(insuranceCompany.getId()));
                mapPatientInsuranceToInsuranceCompany(created, insuranceCompany);
                insuranceCompanyValue=new String[]{insuranceCompany.getName(),insuranceCompany.getId().toString()};
                result.setInsuranceCompany(insuranceCompanyValue);
                break;
            case External:
                InsuranceCompanyExternalEntity insuranceCompanyExternal = createExternalInsuranceCompany(patientInsurance.getInsuranceCompany(), patientInsurance.getInsuranceCompanyAddress());
                mapPatientInsuranceToExternalInsuranceCompany(created, insuranceCompanyExternal);
                insuranceCompanyValue=new String[]{insuranceCompanyExternal.getName(),insuranceCompanyExternal.getId().toString(),insuranceCompanyExternal.getPayerId().toString()};
                result.setInsuranceCompany(insuranceCompanyValue);
                break;
        }
        return result;
    }

    private String[] getAssigner(Long insuranceCompanyId) {
        Optional<InsuranceCompanyPayerEntity> insuranceCompanyPayer = insuranceCompanyPayerRepository
                .findByInternalInsuranceCompany_Id(insuranceCompanyId);
        if (insuranceCompanyPayer.isPresent()) {
            return new String[]{insuranceCompanyPayer.get().getPayerId().toString(), insuranceCompanyPayer.get().getPayer().getDisplayName()};
        }
        return null;
    }

    private InsuranceCompanyEntity createInsuranceCompany(String[] insuranceCompany, BasicAddress insuranceCompanyNameAddress) {
        Optional<InsuranceCompanyEntity> optionalInsuranceCompany = insuranceCompanyRepository.findByInsuranceCompanyName(insuranceCompany[0]).get().stream().findFirst();
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
        Optional<InsuranceCompanyExternalEntity> optionalInsuranceCompanyExternal = insuranceCompanyExternalRepository.findByInsuranceCompanyName(insuranceCompany[0]).get().stream().findFirst();
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
        Optional<PatientInsuranceInternalCompanyEntity> patientInsuranceInternalCompanyEntity = patientInsuranceInternalCompanyRepository.findByInsuranceCompany_IdAndPatientInsurance_Id(insuranceCompany.getId(), patientInsurance.getId());
        if (!patientInsuranceInternalCompanyEntity.isPresent()) {
            PatientInsuranceInternalCompanyEntity patientInsuranceInternalCompany = new PatientInsuranceInternalCompanyEntity();
            patientInsuranceInternalCompany.setPatientInsurance(patientInsurance);
            patientInsuranceInternalCompany.setInsuranceCompany(insuranceCompany);
            patientInsuranceInternalCompanyRepository.save(patientInsuranceInternalCompany);
        }
    }

    private void mapPatientInsuranceToExternalInsuranceCompany(PatientInsuranceEntity patientInsurance, InsuranceCompanyExternalEntity insuranceCompany) {
        Optional<PatientInsuranceExternalCompanyEntity> patientInsuranceExternalCompanyEntity = patientInsuranceExternalCompanyRepository.findByExternalPatientInsurance_IdAndInsuranceCompany_Id(patientInsurance.getId(), insuranceCompany.getId());
        if (!patientInsuranceExternalCompanyEntity.isPresent()) {
            PatientInsuranceExternalCompanyEntity patientInsuranceExternalCompany = new PatientInsuranceExternalCompanyEntity();
            patientInsuranceExternalCompany.setExternalPatientInsurance(patientInsurance);
            patientInsuranceExternalCompany.setInsuranceCompany(insuranceCompany);
            patientInsuranceExternalCompanyRepository.save(patientInsuranceExternalCompany);
        }
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
