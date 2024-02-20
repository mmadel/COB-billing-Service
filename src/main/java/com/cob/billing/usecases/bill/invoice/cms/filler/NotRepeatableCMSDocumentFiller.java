package com.cob.billing.usecases.bill.invoice.cms.filler;

import com.cob.billing.model.bill.cms.CMSFields;
import com.cob.billing.model.bill.invoice.tmp.*;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class NotRepeatableCMSDocumentFiller {
    private PdfAcroForm cmsForm;

    public void fill(InvoiceRequest invoiceRequest, PdfAcroForm cmsForm) {
        this.cmsForm = cmsForm;
        cmsForm.getField(CMSFields.INSURANCE_ID).setValue(invoiceRequest.getInvoicePatientInsuredInformation().getPrimaryId());
        fillCarrier(invoiceRequest.getInvoiceInsuranceCompanyInformation());
        fillPatient(invoiceRequest.getPatientInformation());
        fillPatientInsured(invoiceRequest.getInvoicePatientInsuredInformation());
        fillBillingProvider(invoiceRequest.getInvoiceBillingProviderInformation());
    }

    private void fillCarrier(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation) {
        String assigned[] = invoiceInsuranceCompanyInformation.getAssigner();
        if (assigned == null) {
            cmsForm.getField(CMSFields.INSURANCE_NAME).setValue(invoiceInsuranceCompanyInformation.getName());
            cmsForm.getField(CMSFields.INSURANCE_ADDRESS).setValue(invoiceInsuranceCompanyInformation.getAddress().getAddress() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getAddress());
            cmsForm.getField(CMSFields.INSURANCE_ADDRESS2).setValue("");
            cmsForm.getField(CMSFields.INSURANCE_CITY_STATE_ZIP).setValue(invoiceInsuranceCompanyInformation.getAddress().getCity() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getCity()
                    + "," + invoiceInsuranceCompanyInformation.getAddress().getState() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getState()
                    + " " + invoiceInsuranceCompanyInformation.getAddress().getZipCode() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getZipCode());
            cmsForm.getField("assignment").setValue(invoiceInsuranceCompanyInformation.getIsAssignment() ? "YES" : "NO", false);
            cmsForm.getField("ins_signature").setValue(invoiceInsuranceCompanyInformation.getSignature());
        } else {
            cmsForm.getField(CMSFields.INSURANCE_NAME).setValue(assigned[1]);
            cmsForm.getField(CMSFields.INSURANCE_ADDRESS).setValue(assigned[2]);
            cmsForm.getField(CMSFields.INSURANCE_CITY_STATE_ZIP).setValue(assigned[3]);
        }
        switch (invoiceInsuranceCompanyInformation.getInsuranceType()) {
            case "Medicare_Part_A":
            case "Medicare_Part_B":
                cmsForm.getField(CMSFields.INSURANCE_TYPE).setValue("Medicare", false);
                break;
            case "Medicaid":
                cmsForm.getField(CMSFields.INSURANCE_TYPE).setValue("Medicaid",false);
                break;
            default:
                cmsForm.getField(CMSFields.INSURANCE_TYPE).setValue("Group",false);
        }
    }

    private void fillPatient(InvoicePatientInformation patientInformation) {
        String[] patientDOB = DateConstructor.construct(patientInformation.getDateOfBirth());
        cmsForm.getField("pt_name").setValue(patientInformation.getLastName() + "," + patientInformation.getFirstName());
        cmsForm.getField("birth_mm").setValue(patientDOB[0]);
        cmsForm.getField("birth_dd").setValue(patientDOB[1]);
        cmsForm.getField("birth_yy").setValue(patientDOB[2]);
        cmsForm.getField("sex").setValue(patientInformation.getGender().name().equals("Male") ? "M" : "F", false);
        cmsForm.getField("pt_street").setValue(patientInformation.getAddress().getFirst());
        cmsForm.getField("pt_city").setValue(patientInformation.getAddress().getCity());
        cmsForm.getField("pt_state").setValue(patientInformation.getAddress().getState());
        cmsForm.getField("pt_zip").setValue(patientInformation.getAddress().getZipCode());
        cmsForm.getField("pt_AreaCode").setValue(patientInformation.getPhone().substring(1, 4));
        cmsForm.getField("pt_phone").setValue(patientInformation.getPhone()
                .substring(5, patientInformation.getPhone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));

        if (patientInformation.getPatientAdvancedInformation() != null) {
            if (patientInformation.getPatientAdvancedInformation().getUnableToWorkStartDate() != null
                    && patientInformation.getPatientAdvancedInformation().getUnableToWorkEndDate() != null) {
                String[] unableToWorkDateStartDate = DateConstructor.construct(patientInformation.getPatientAdvancedInformation().getUnableToWorkStartDate());
                String[] unableToWorkDateEndDate = DateConstructor.construct(patientInformation.getPatientAdvancedInformation().getUnableToWorkEndDate());
                cmsForm.getField("work_mm_from").setValue(unableToWorkDateStartDate[0]);
                cmsForm.getField("work_dd_from").setValue(unableToWorkDateStartDate[1]);
                cmsForm.getField("work_yy_from").setValue(unableToWorkDateStartDate[2]);

                cmsForm.getField("work_mm_end").setValue(unableToWorkDateEndDate[0]);
                cmsForm.getField("work_dd_end").setValue(unableToWorkDateEndDate[1]);
                cmsForm.getField("work_yy_end").setValue(unableToWorkDateEndDate[2]);
            }

            if (patientInformation.getPatientAdvancedInformation().getHospitalizedStartDate() != null
                    && patientInformation.getPatientAdvancedInformation().getHospitalizedEndDate() != null) {
                String[] hospitalizedStartDate = DateConstructor.construct(patientInformation.getPatientAdvancedInformation().getHospitalizedStartDate());
                String[] hospitalizedEndDate = DateConstructor.construct(patientInformation.getPatientAdvancedInformation().getHospitalizedEndDate());
                cmsForm.getField("hosp_mm_from").setValue(hospitalizedStartDate[0]);
                cmsForm.getField("hosp_dd_from").setValue(hospitalizedStartDate[1]);
                cmsForm.getField("hosp_yy_from").setValue(hospitalizedStartDate[2]);

                cmsForm.getField("hosp_mm_end").setValue(hospitalizedEndDate[0]);
                cmsForm.getField("hosp_dd_end").setValue(hospitalizedEndDate[1]);
                cmsForm.getField("hosp_yy_end").setValue(hospitalizedEndDate[2]);
            }
            if (patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion() != null) {
                cmsForm.getField("employment").setValue(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment() ? "YES" : "NO", false);
                cmsForm.getField("pt_auto_accident").setValue(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident() ? "YES" : "NO", false);
                cmsForm.getField("other_accident").setValue(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isOtherAccident() ? "YES" : "NO", false);
                cmsForm.getField("accident_place").setValue("");
            }
        }
    }

    private void fillPatientInsured(InvoicePatientInsuredInformation invoicePatientInsuredInformation) {
        String[] insuredDOB = DateConstructor.construct(invoicePatientInsuredInformation.getDateOfBirth());
        cmsForm.getField("ins_name").setValue(invoicePatientInsuredInformation.getLastName() + "," + invoicePatientInsuredInformation.getFirstName());
        switch (invoicePatientInsuredInformation.getRelationToInsured()){
            case "Self":
                cmsForm.getField("rel_to_ins").setValue("S", false);
                break;
            case "Spouse":
                cmsForm.getField("rel_to_ins").setValue("M", false);
                break;
            case "Parent":
                cmsForm.getField("rel_to_ins").setValue("C", false);
                break;
            case "Other":
                cmsForm.getField("rel_to_ins").setValue("O", false);
                break;
        }
        cmsForm.getField("ins_street").setValue(invoicePatientInsuredInformation.getAddress().getFirst());
        cmsForm.getField("ins_city").setValue(invoicePatientInsuredInformation.getAddress().getCity());
        cmsForm.getField("ins_state").setValue(invoicePatientInsuredInformation.getAddress().getState());
        cmsForm.getField("ins_zip").setValue(invoicePatientInsuredInformation.getAddress().getZipCode());
        cmsForm.getField("ins_phone area").setValue(invoicePatientInsuredInformation.getPhone().substring(1, 4));
        cmsForm.getField("ins_phone").setValue(invoicePatientInsuredInformation.getPhone()
                .substring(5, invoicePatientInsuredInformation.getPhone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("ins_policy").setValue("");
        cmsForm.getField("ins_sex").setValue(invoicePatientInsuredInformation.getGender().name().equals("Male") ? "MALE" : "FEMALE", false);
        cmsForm.getField("ins_dob_mm").setValue(insuredDOB[0]);
        cmsForm.getField("ins_dob_dd").setValue(insuredDOB[1]);
        cmsForm.getField("ins_dob_yy").setValue(insuredDOB[2]);
        cmsForm.getField("other_ins_name").setValue("");
        cmsForm.getField("other_ins_policy").setValue("");
    }

    private void fillBillingProvider(InvoiceBillingProviderInformation invoiceBillingProviderInformation) {
        cmsForm.getField("doc_name").setValue(invoiceBillingProviderInformation.getBusinessName());
        cmsForm.getField("doc_street").setValue(invoiceBillingProviderInformation.getAddress());
        cmsForm.getField("doc_location").setValue(invoiceBillingProviderInformation.getCity_state_zip());
        cmsForm.getField("doc_phone area").setValue(invoiceBillingProviderInformation.getPhone().substring(1, 4));
        cmsForm.getField("doc_phone").setValue(invoiceBillingProviderInformation.getPhone()
                .substring(5, invoiceBillingProviderInformation.getPhone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("tax_id").setValue(invoiceBillingProviderInformation.getTaxId());
        cmsForm.getField("ssn").setValue("EIN", false);
        cmsForm.getField("pin").setValue(invoiceBillingProviderInformation.getNpi());
        cmsForm.getField("grp").setValue(invoiceBillingProviderInformation.getTaxonomy());
        //npi for location
        cmsForm.getField("pin1").setValue(invoiceBillingProviderInformation.getNpi());
    }
}
