package com.cob.billing.usecases.bill;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindInsuranceCompaniesUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;

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
}
