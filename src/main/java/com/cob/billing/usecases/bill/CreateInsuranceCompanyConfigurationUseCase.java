package com.cob.billing.usecases.bill;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateInsuranceCompanyConfigurationUseCase {
    private static final Long DEFAULT_ORGANIZATION = -1l;
    @Autowired
    ModelMapper mapper;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;

    @Transactional
    public void create(InsuranceCompanyConfiguration model) {
        InsuranceCompanyConfigurationEntity toBeCreated =
                mapper.map(model, InsuranceCompanyConfigurationEntity.class);
        if (model.getBillingProvider() != null) {
            OrganizationEntity otherOrganization =
                    mapper.map(model.getBillingProvider(), OrganizationEntity.class);
            Long createdOtherOrganizationId = organizationRepository.save(otherOrganization).getId();
            toBeCreated.setBox33(createdOtherOrganizationId);
        } else
            toBeCreated.setBox33(DEFAULT_ORGANIZATION);
        insuranceCompanyConfigurationRepository.save(toBeCreated);
    }
}
