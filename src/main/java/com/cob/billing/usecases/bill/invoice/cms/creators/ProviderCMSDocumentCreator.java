package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class ProviderCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(OrganizationEntity organization) {
        cmsForm.getField("doc_name").setValue(organization.getBusinessName());
        cmsForm.getField("doc_street").setValue(organization.getOrganizationData().getAddress());
        cmsForm.getField("doc_location").setValue(organization.getOrganizationData().getCity()
                + "," + organization.getOrganizationData().getState() + " " + organization.getOrganizationData().getZipcode());
        cmsForm.getField("doc_phone area").setValue(organization.getOrganizationData().getPhone().substring(1, 4));
        cmsForm.getField("doc_phone").setValue(organization.getOrganizationData().getPhone()
                .substring(5, organization.getOrganizationData().getPhone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("tax_id").setValue(organization.getOrganizationData().getTaxId());
        cmsForm.getField("ssn").setValue("EIN",false);
    }
}
