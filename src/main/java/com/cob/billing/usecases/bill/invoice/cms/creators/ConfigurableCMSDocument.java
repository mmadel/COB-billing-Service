package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.repositories.admin.ClinicRepository;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigurableCMSDocument {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ProviderCMSDocumentCreator providerCMSDocumentCreator;
    @Autowired
    LocationCMSDocumentCreator locationCMSDocumentCreator;
    @Autowired
    ClinicRepository clinicRepository;

    public PdfAcroForm cmsForm;

    public void create(InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration, String clinicName
            , String primaryId
            , String patientSSN
            , String patientExternalID) {
        OrganizationEntity organization = null;
        if (insuranceCompanyConfiguration.getBox33() == -1L)
            organization = organizationRepository.findByType(OrganizationType.Default).get();
        else
            organization = organizationRepository.findById(insuranceCompanyConfiguration.getBox33()).get();
        if (organization != null) {
            providerCMSDocumentCreator.cmsForm = cmsForm;
            providerCMSDocumentCreator.create(organization);
        }
//        if (insuranceCompanyConfiguration.getBox32()) {
//            String title = clinicName;
//            ClinicEntity clinic = clinicRepository.findByTitle(title).get();
//            if (clinic != null) {
//                locationCMSDocumentCreator.cmsForm = cmsForm;
//                locationCMSDocumentCreator.create(clinic);
//            }
//        }
        if (insuranceCompanyConfiguration.getBox26().equals("insured_primary_id"))
            cmsForm.getField("pt_account").setValue(primaryId);
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_ssn"))
            cmsForm.getField("pt_account").setValue(patientSSN);
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_external_id"))
            cmsForm.getField("pt_account").setValue(patientExternalID);
    }
}
