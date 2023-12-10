package com.cob.billing.usecases.bill.insurance.company;

import com.cob.billing.model.bill.insurance.company.InsuranceCompany;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindInsuranceCompaniesUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    ModelMapper mapper;

    public List<InsuranceCompany> find() {
        return insuranceCompanyRepository.findAll().stream()
                .map(insuranceCompanyEntity -> mapper.map(insuranceCompanyEntity, InsuranceCompany.class))
                .collect(Collectors.toList());
    }
}
