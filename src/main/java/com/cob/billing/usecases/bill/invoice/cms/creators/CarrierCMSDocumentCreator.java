package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class CarrierCMSDocumentCreator {
    public PdfAcroForm cmsForm;
    public void create(PatientInsuranceEntity insuranceCompany) {
        cmsForm.getField("insurance_name").setValue(insuranceCompany.getPatientInsurancePolicy().getPayerName());
        cmsForm.getField("insurance_address").setValue(insuranceCompany.getPayerAddress().getAddress());
        cmsForm.getField("insurance_address2").setValue("");
        cmsForm.getField("insurance_city_state_zip").setValue(insuranceCompany.getPayerAddress().getCity()
                + "," +insuranceCompany.getPayerAddress().getState() +" " + insuranceCompany.getPayerAddress().getZipCode());
        cmsForm.getField("insurance_type").setValue("Group",false);
        cmsForm.getField("insurance_id").setValue(insuranceCompany.getPatientInsurancePolicy().getPrimaryId());
    }
}
