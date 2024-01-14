package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.bill.cms.CMSFields;
import com.cob.billing.usecases.bill.FindInsuranceCompaniesUseCase;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarrierCMSDocumentCreator {
    @Autowired
    FindInsuranceCompaniesUseCase findInsuranceCompaniesUseCase;
    public PdfAcroForm cmsForm;
    private String[] data = new String[3];

    public void create(PatientInsuranceEntity patientInsuranceCompany) {

        getInsuranceCompanyData(patientInsuranceCompany);

        cmsForm.getField(CMSFields.INSURANCE_NAME).setValue(data[0]);
        cmsForm.getField(CMSFields.INSURANCE_ADDRESS).setValue(data[1]);
        cmsForm.getField(CMSFields.INSURANCE_ADDRESS2).setValue("");
        cmsForm.getField(CMSFields.INSURANCE_CITY_STATE_ZIP).setValue(data[2]);
        cmsForm.getField(CMSFields.INSURANCE_TYPE).setValue("Group", false);
        cmsForm.getField(CMSFields.INSURANCE_ID).setValue(patientInsuranceCompany.getPatientInsurancePolicy().getPrimaryId());
    }

    private String[] getInsuranceCompanyData(PatientInsuranceEntity patientInsuranceCompany) {
        if (patientInsuranceCompany.getPatientInsuranceInternalCompany() != null) {
            InsuranceCompanyEntity insuranceCompany = patientInsuranceCompany.getPatientInsuranceInternalCompany().getInsuranceCompany();
            String[] payer = findInsuranceCompaniesUseCase.findInternalPayer(insuranceCompany.getId());
            if (payer == null) {
                data[0] = insuranceCompany.getName();
                data[1] = insuranceCompany.getAddress().getAddress();
                data[2] = insuranceCompany.getAddress().getCity()
                        + "," + insuranceCompany.getAddress().getState() + " " + insuranceCompany.getAddress().getZipCode();
            } else {
                data[0] = payer[1];
                data[1] = payer[2];
                data[2] = payer[3];
            }
        }

        if (patientInsuranceCompany.getPatientInsuranceExternalCompany() != null) {
            InsuranceCompanyExternalEntity insuranceCompanyExternal = patientInsuranceCompany.getPatientInsuranceExternalCompany().getInsuranceCompany();
            data[0] = insuranceCompanyExternal.getName();
            data[1] = insuranceCompanyExternal.getAddress().getAddress();
            data[2] = insuranceCompanyExternal.getAddress().getCity()
                    + "," + insuranceCompanyExternal.getAddress().getState() + " " + insuranceCompanyExternal.getAddress().getZipCode();
        }
        return data;
    }
}
