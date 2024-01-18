package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateInsuranceCompanyConfigurationUseCase {
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;
    InsuranceCompanyConfigurationEntity toBeUpdated;

    public void update(InsuranceCompanyConfiguration model) {
        toBeUpdated =
                insuranceCompanyConfigurationRepository.findById(model.getId()).get();
        updateBillingProvider(model.getBillingProvider());
        toBeUpdated.setBox26(model.getBox26());
        toBeUpdated.setBox32(model.getBox32());
        insuranceCompanyConfigurationRepository.save(toBeUpdated);
    }

    private void updateBillingProvider(Organization billingProvider) {
        if (billingProvider.getId() == null) {
            OrganizationEntity otherOrganization =
                    mapper.map(billingProvider, OrganizationEntity.class);
            Long createdOtherOrganizationId = organizationRepository.save(otherOrganization).getId();
            toBeUpdated.setBox33(createdOtherOrganizationId);
        }
    }
}
