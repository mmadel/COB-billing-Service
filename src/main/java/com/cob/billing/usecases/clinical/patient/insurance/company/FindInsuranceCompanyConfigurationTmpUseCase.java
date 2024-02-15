package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindInsuranceCompanyConfigurationTmpUseCase {
    private final static int BILLING_PROVIDER_NAME = 0;
    private final static int BILLING_PROVIDER_ADDRESS = 1;
    private final static int BILLING_PROVIDER_CITY_STATE_ZIPCODE = 2;
    private final static int BILLING_PROVIDER_PHONE = 3;
    private final static int BILLING_PROVIDER_TAX_ID = 4;
    private final static int PATIENT_ACCOUNT = 5;
    private final static int BILLING_PROVIDER_NPI = 6;
    private final static int BILLING_PROVIDER_NPI_TAXONOMY = 7;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;

    public InsuranceCompanyConfiguration find(Long id, InsuranceCompanyVisibility visibility) {
        InsuranceCompanyConfigurationEntity insuranceCompanyConfigurationEntity = null;
        InsuranceCompanyConfiguration insuranceCompanyConfiguration = new InsuranceCompanyConfiguration();
        switch (visibility) {
            case Internal:
                insuranceCompanyConfigurationEntity = insuranceCompanyConfigurationRepository.findByInternalInsuranceCompany_Id(id).get();

                break;
            case External:
                insuranceCompanyConfigurationEntity = insuranceCompanyConfigurationRepository.findByExternalInsuranceCompany_Id(id).get();
                break;
        }
        insuranceCompanyConfiguration.setBillingProvider(mapper.map(getBillingProvider(insuranceCompanyConfigurationEntity.getBox33()), Organization.class));
        insuranceCompanyConfiguration.setBox26(insuranceCompanyConfigurationEntity.getBox26());
        insuranceCompanyConfiguration.setBox32(insuranceCompanyConfigurationEntity.getBox32());
        insuranceCompanyConfiguration.setId(insuranceCompanyConfigurationEntity.getId());
        return insuranceCompanyConfiguration;
    }

    public String[] findElement(Long id, InsuranceCompanyVisibility visibility) {
        InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration = null;
        OrganizationEntity organization = null;
        String[] result = new String[8];
        switch (visibility) {
            case Internal:
                insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository.findByInternalInsuranceCompany_Id(id).get();

                break;
            case External:
                insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository.findByExternalInsuranceCompany_Id(id).get();
                break;
        }
        organization = getBillingProvider(insuranceCompanyConfiguration.getBox33());


        if (organization != null) {
            result[BILLING_PROVIDER_NAME] = organization.getBusinessName();
            result[BILLING_PROVIDER_ADDRESS] = organization.getOrganizationData().getAddress();
            result[BILLING_PROVIDER_CITY_STATE_ZIPCODE] = organization.getOrganizationData().getCity()
                    + "," + organization.getOrganizationData().getState() + " " + organization.getOrganizationData().getZipcode();
            result[BILLING_PROVIDER_PHONE] = organization.getOrganizationData().getPhone();
            result[BILLING_PROVIDER_TAX_ID] = organization.getOrganizationData().getTaxId();
        }
        if (insuranceCompanyConfiguration.getBox26().equals("insured_primary_id"))
            result[PATIENT_ACCOUNT] = "primaryId";
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_ssn"))
            result[PATIENT_ACCOUNT] = "ssn";
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_external_id"))
            result[PATIENT_ACCOUNT] = "externalId";
        result[BILLING_PROVIDER_NPI] = organization.getNpi();
        result[BILLING_PROVIDER_NPI_TAXONOMY] = organization.getOrganizationData().getTaxonomy();
        return result;
    }

    private OrganizationEntity getBillingProvider(Long box33) {
        OrganizationEntity organization = null;
        if (box33 == -1L)
            organization = organizationRepository.findByType(OrganizationType.Default).get();
        else
            organization = organizationRepository.findById(box33).get();
        return organization;
    }
}
