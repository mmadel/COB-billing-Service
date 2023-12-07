package com.cob.billing.usecases.bill.insurance.company;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.model.bill.insurance.company.InsuranceCompany;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddInsuranceCompanyUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    InsuranceCompanyRepository repository;

    public void add(List<InsuranceCompany> models) {
        List<InsuranceCompanyEntity> entities = models.stream()
                .map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyEntity.class))
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }
}
