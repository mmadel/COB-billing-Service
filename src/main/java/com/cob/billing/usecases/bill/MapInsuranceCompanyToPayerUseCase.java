package com.cob.billing.usecases.bill;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.bill.payer.PayerEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapInsuranceCompanyToPayerUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;
    @Autowired
    PayerRepository payerRepository;

    public void mapAll(List<InsuranceCompanyMapper> insuranceCompanyMappers) {
        List<Long> insuranceCompaniesIds = insuranceCompanyMappers.stream()
                .map(InsuranceCompanyMapper::getInsuranceCompanyId).collect(Collectors.toList());

        List<InsuranceCompanyEntity> entities = insuranceCompanyRepository.findAllById(insuranceCompaniesIds);
        entities.stream()
                .forEach(insuranceCompanyEntity -> {
                    Long id = insuranceCompanyEntity.getId();
                    insuranceCompanyMappers.stream()
                            .filter(insuranceCompanyMapper -> insuranceCompanyMapper.getInsuranceCompanyId() == id)
                            .findFirst()
                            .ifPresent(insuranceCompanyMapper -> {
                                //insuranceCompanyEntity.setPayerId(insuranceCompanyMapper.getPayerId());
                                changePatientInsurancePayer(insuranceCompanyEntity.getId(), insuranceCompanyMapper.getPayerId());
                            });
                });
        insuranceCompanyRepository.saveAll(entities);
    }

    public void map(InsuranceCompanyMapper insuranceCompanyMapper) {
        InsuranceCompanyEntity entity = insuranceCompanyRepository.findById(insuranceCompanyMapper.getInsuranceCompanyId())
                .get();
        //entity.setPayerId(insuranceCompanyMapper.getPayerId());
        changePatientInsurancePayer(entity.getId(), insuranceCompanyMapper.getPayerId());
        insuranceCompanyRepository.save(entity);
    }

    private void changePatientInsurancePayer(Long insuranceCompanyId, Long payerId) {
        PatientInsuranceEntity patientInsurance = null;
        PayerEntity payer = payerRepository.findByPayerId(payerId).get();
        //patientInsurance.getPatientInsurancePolicy().setPayerId(payer.getPayerId().toString());
        //patientInsurance.getPatientInsurancePolicy().setPayerName(payer.getName());
        patientInsuranceRepository.save(patientInsurance);
    }
}
