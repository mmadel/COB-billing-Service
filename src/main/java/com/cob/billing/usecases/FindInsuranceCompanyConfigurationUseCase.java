package com.cob.billing.usecases;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    public InsuranceCompanyConfiguration find(Long identifier, InsuranceCompanyVisibility insuranceCompanyVisibility) {
        InsuranceCompanyConfigurationEntity configuration = getConfiguration(identifier, insuranceCompanyVisibility);
        Organization organization = null;
        if (configuration == null)
            return null;
        if (configuration.getBox33() != -1L) {
            organization = mapper.map(organizationRepository.findById(configuration.getBox33()).get(), Organization.class);
        }
        InsuranceCompanyConfiguration insuranceCompanyConfiguration = mapInsuranceCompanyConfiguration(configuration);
        if (organization != null)
            insuranceCompanyConfiguration.setBillingProvider(organization);
        return insuranceCompanyConfiguration;
    }

    private InsuranceCompanyConfigurationEntity getConfiguration(Long id, InsuranceCompanyVisibility insuranceCompanyVisibility) {
        InsuranceCompanyConfigurationEntity configuration = null;
        switch (insuranceCompanyVisibility) {
            case Internal:
                configuration = insuranceCompanyConfigurationRepository.findByInternalInsuranceCompany_Id(id).get();
                break;
            case External:
                configuration = insuranceCompanyConfigurationRepository.findByExternalInsuranceCompany_Id(id).get();
                break;
        }
        return configuration;
    }

    private InsuranceCompanyConfiguration mapInsuranceCompanyConfiguration(InsuranceCompanyConfigurationEntity entity) {
        InsuranceCompanyConfiguration insuranceCompanyConfiguration;
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<InsuranceCompanyConfigurationEntity, InsuranceCompanyConfiguration>() {
            @Override
            protected void configure() {
                skip(destination.getInsuranceCompanyId());
            }
        });
        insuranceCompanyConfiguration = mapper.map(entity, InsuranceCompanyConfiguration.class);
        if (entity.getInternalInsuranceCompany() != null)
            insuranceCompanyConfiguration.setInsuranceCompanyId(entity.getInternalInsuranceCompany().getId());

        if (entity.getExternalInsuranceCompany() != null) {
            insuranceCompanyConfiguration.setInsuranceCompanyId(entity.getExternalInsuranceCompany().getId());
        }
        return insuranceCompanyConfiguration;
    }
}
