package com.cob.billing.usecases;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindInsuranceCompanyConfigurationUseCase {
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;

    public InsuranceCompanyConfiguration find(Long identifier) {
        InsuranceCompanyConfigurationEntity configuration = null;
        Organization organization = null;
        if (configuration == null)
            return null;
        if (configuration.getBox33() != -1L) {
            organization = mapper.map(organizationRepository.findById(configuration.getBox33()).get(), Organization.class);
        }
        InsuranceCompanyConfiguration insuranceCompanyConfiguration = mapper.map(configuration, InsuranceCompanyConfiguration.class);
        if (organization != null)
            insuranceCompanyConfiguration.setBillingProvider(organization);
        return insuranceCompanyConfiguration;
    }
}
