package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindInsuranceCompanyConfigurationTmpUseCase {
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    public String[] find(Long id, InsuranceCompanyVisibility visibility) {
        InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration = null;
        OrganizationEntity organization = null;
        String[] result = new String[5];
        switch (visibility) {
            case Internal:
                insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository.findByInternalInsuranceCompany_Id(id).get();

                break;
            case External:
                insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository.findByExternalInsuranceCompany_Id(id).get();
                break;
        }
        if (insuranceCompanyConfiguration.getBox33() == -1L)
            organization = organizationRepository.findByType(OrganizationType.Default).get();
        else
            organization = organizationRepository.findById(insuranceCompanyConfiguration.getBox33()).get();

        if(organization != null){
            result[0] = organization.getBusinessName();
            result[1] = organization.getOrganizationData().getAddress();
            result[2] = organization.getOrganizationData().getCity()
                    + "," + organization.getOrganizationData().getState() + " " + organization.getOrganizationData().getZipcode();
            result[3] = organization.getOrganizationData().getPhone();
        }
        if (insuranceCompanyConfiguration.getBox26().equals("insured_primary_id"))
            result[4] = "primaryId";
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_ssn"))
            result[4] = "ssn";
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_external_id"))
            result[4] = "externalId";
        return result;
    }
}
