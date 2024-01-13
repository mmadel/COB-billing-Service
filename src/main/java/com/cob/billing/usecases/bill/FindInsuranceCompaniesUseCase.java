package com.cob.billing.usecases.bill;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyPayerEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompany;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyPayerRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindInsuranceCompaniesUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    InsuranceCompanyPayerRepository insuranceCompanyPayerRepository;

    public List<InsuranceCompanyHolder> find() {
        List<InsuranceCompanyHolder> insuranceCompanyHolder = new ArrayList<>();
        insuranceCompanyRepository.findAll()
                .stream()
                .forEach(insuranceCompany -> {
                    InsuranceCompanyHolder internalHolder = new InsuranceCompanyHolder();
                    internalHolder.setId(insuranceCompany.getId());
                    internalHolder.setName(insuranceCompany.getName());
                    internalHolder.setVisibility(InsuranceCompanyVisibility.Internal);
                    insuranceCompanyHolder.add(internalHolder);
                });
        insuranceCompanyExternalRepository.findAll().stream()
                .forEach(insuranceCompanyExternal -> {
                    InsuranceCompanyHolder internalHolder = new InsuranceCompanyHolder();
                    internalHolder.setId(insuranceCompanyExternal.getId());
                    internalHolder.setName(insuranceCompanyExternal.getName());
                    internalHolder.setVisibility(InsuranceCompanyVisibility.External);
                    insuranceCompanyHolder.add(internalHolder);
                });
        return insuranceCompanyHolder;
    }

    public List<InsuranceCompany> findInternal() {
        List<InsuranceCompany> insuranceCompanies = new ArrayList<>();
        insuranceCompanyRepository.findAll().stream()
                .forEach(insuranceCompany -> {
                    InsuranceCompany internalHolder = new InsuranceCompany();
                    internalHolder.setId(insuranceCompany.getId());
                    internalHolder.setName(insuranceCompany.getName());
                    internalHolder.setAddress(insuranceCompany.getAddress());
                    insuranceCompanies.add(internalHolder);
                });
        List<Long> ids = insuranceCompanies.stream().map(InsuranceCompany::getId).collect(Collectors.toList());
        List<InsuranceCompanyPayerEntity> companyPayerEntities = insuranceCompanyPayerRepository.findByInternalInsuranceCompanies(ids);
        insuranceCompanies.stream()
                .forEach(insuranceCompany -> {
                    companyPayerEntities.stream()
                            .forEach(insuranceCompanyPayerEntity -> {
                                if (insuranceCompany.getId().equals(insuranceCompanyPayerEntity.getInternalInsuranceCompany().getId())) {
                                    String[] assignedPayer = {insuranceCompanyPayerEntity.getPayer().getPayerId().toString()
                                            , insuranceCompanyPayerEntity.getPayer().getDisplayName()
                                            , insuranceCompanyPayerEntity.getId().toString()};
                                    insuranceCompany.setAssigner(assignedPayer);
                                }
                            });
                });
        return insuranceCompanies;
    }
}
