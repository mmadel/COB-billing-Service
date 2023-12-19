package com.cob.billing.usecases.bill.insurance.company;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.model.bill.insurance.company.InsuranceCompany;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddInsuranceCompanyUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    InsuranceCompanyRepository repository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;

    public void add(List<InsuranceCompany> models) {
        List<InsuranceCompanyEntity> entities = models.stream()
                .map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyEntity.class))
                .collect(Collectors.toList());
        List<InsuranceCompanyEntity> added = repository.saveAll(entities);
        List<InsuranceCompanyConfigurationEntity> insuranceCompanyConfigurationEntities = new ArrayList<>();
        added.stream()
                .forEach(insuranceCompanyEntity -> {
                    InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration = new InsuranceCompanyConfigurationEntity();
                    insuranceCompanyConfiguration.setBox32(false);
                    insuranceCompanyConfiguration.setBox26("insured_primary_id");
                    insuranceCompanyConfiguration.setInsuranceCompanyIdentifier(insuranceCompanyEntity.getId());
                    insuranceCompanyConfiguration.setBox33(-1L);
                    insuranceCompanyConfigurationEntities.add(insuranceCompanyConfiguration);
                    insuranceCompanyConfiguration.setIsAssignedToPayer(false);
                });
        insuranceCompanyConfigurationRepository.saveAll(insuranceCompanyConfigurationEntities);
    }
}
