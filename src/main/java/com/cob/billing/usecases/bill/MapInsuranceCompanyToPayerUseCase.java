package com.cob.billing.usecases.bill;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapInsuranceCompanyToPayerUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

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
                            .ifPresent(insuranceCompanyMapper -> insuranceCompanyEntity.setPayerId(insuranceCompanyMapper.getPayerId()));
                });
        insuranceCompanyRepository.saveAll(entities);
    }

    public void map(InsuranceCompanyMapper insuranceCompanyMapper) {
        InsuranceCompanyEntity entity = insuranceCompanyRepository.findById(insuranceCompanyMapper.getInsuranceCompanyId())
                .get();
        entity.setPayerId(insuranceCompanyMapper.getPayerId());
        insuranceCompanyRepository.save(entity);
    }
}
