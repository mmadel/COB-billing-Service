package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyPayerEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyPayerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MapPatientUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    InsuranceCompanyPayerRepository insuranceCompanyPayerRepository;

    public Patient map(PatientEntity entity) {
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientEntity, Patient>() {
            @Override
            protected void configure() {
                skip(destination.getPatientInsurances());
            }
        });
        Patient patient = mapper.map(entity, Patient.class);
        List<PatientInsurance> patientInsurances = new ArrayList<>();
        entity.getInsurances().stream()
                .forEach(patientInsuranceEntity -> {
                    PatientInsurance patientInsurance = new PatientInsurance();
                    mapPatientInsurance(patientInsuranceEntity, patientInsurance);
                    if (patientInsuranceEntity.getPatientInsuranceExternalCompany() != null) {
                        patientInsurance.setVisibility(InsuranceCompanyVisibility.External);
                        String[] insuranceCompany = {patientInsuranceEntity.getPatientInsuranceExternalCompany().getInsuranceCompany().getName(),
                                patientInsuranceEntity.getPatientInsuranceExternalCompany().getInsuranceCompany().getId().toString()};
                        patientInsurance.setInsuranceCompany(insuranceCompany);
                        patientInsurance.setInsuranceCompanyAddress(patientInsuranceEntity.getPatientInsuranceExternalCompany().getInsuranceCompany().getAddress());
                    }
                    if (patientInsuranceEntity.getPatientInsuranceInternalCompany() != null) {
                        patientInsurance.setVisibility(InsuranceCompanyVisibility.Internal);
                        String[] insuranceCompany = {patientInsuranceEntity.getPatientInsuranceInternalCompany().getInsuranceCompany().getName(),
                                patientInsuranceEntity.getPatientInsuranceInternalCompany().getInsuranceCompany().getId().toString()};
                        Optional<InsuranceCompanyPayerEntity> insuranceCompanyPayer = insuranceCompanyPayerRepository
                                .findByInternalInsuranceCompany_Id(patientInsuranceEntity.getPatientInsuranceInternalCompany()
                                        .getInsuranceCompany().getId());
                        if (insuranceCompanyPayer.isPresent()) {
                            String[] assigner = {insuranceCompanyPayer.get().getPayerId().toString()
                                    , insuranceCompanyPayer.get().getPayer().getDisplayName()
                                    , insuranceCompanyPayer.get().getPayer().getAddress().getAddress()
                                    , insuranceCompanyPayer.get().getPayer().getAddress().getCity()
                                    + "," + insuranceCompanyPayer.get().getPayer().getAddress().getState() + " " + insuranceCompanyPayer.get().getPayer().getAddress().getZipCode()};
                            patientInsurance.setAssigner(assigner);
                        }
                        patientInsurance.setInsuranceCompany(insuranceCompany);
                        patientInsurance.setInsuranceCompanyAddress(patientInsuranceEntity.getPatientInsuranceInternalCompany().getInsuranceCompany().getAddress());
                    }
                    patientInsurances.add(patientInsurance);
                });
        patient.setPatientInsurances(patientInsurances);
        return patient;
    }

    public void mapPatientInsurance(PatientInsuranceEntity source, PatientInsurance destination) {
        destination.setId(source.getId());
        destination.setRelation(source.getRelation());
        destination.setPatientRelation(source.getPatientRelation());
        destination.setPatientInsurancePolicy(source.getPatientInsurancePolicy());
        destination.setPatientInsuranceAdvanced(source.getPatientInsuranceAdvanced());
        destination.setIsArchived(source.getIsArchived());
    }
}
