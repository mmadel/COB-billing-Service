package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class InsuredCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(PatientInsuranceEntity insuranceCompany) {
        String[] insuredDOB = DateConstructor.construct(insuranceCompany.getPatientRelation().getR_birthDate());
        cmsForm.getField("ins_name").setValue(insuranceCompany.getPatientRelation().getR_lastName() + "," + insuranceCompany.getPatientRelation().getR_firstName());
        cmsForm.getField("rel_to_ins").setValue(insuranceCompany.getRelation().substring(0, 1), false);
        cmsForm.getField("ins_street").setValue(insuranceCompany.getPatientRelation().getR_address().getFirst());
        cmsForm.getField("ins_city").setValue(insuranceCompany.getPatientRelation().getR_address().getCity());
        cmsForm.getField("ins_state").setValue(insuranceCompany.getPatientRelation().getR_address().getState());
        cmsForm.getField("ins_zip").setValue(insuranceCompany.getPatientRelation().getR_address().getZipCode());
        cmsForm.getField("ins_phone area").setValue(insuranceCompany.getPatientRelation().getR_phone().substring(1, 4));
        cmsForm.getField("ins_phone").setValue(insuranceCompany.getPatientRelation().getR_phone()
                .substring(5, insuranceCompany.getPatientRelation().getR_phone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("ins_policy").setValue("");
        cmsForm.getField("ins_sex").setValue(insuranceCompany.getPatientRelation().getR_gender().equals("Male") ? "MALE" : "FEMALE", false);
        cmsForm.getField("ins_dob_dd").setValue(insuredDOB[0]);
        cmsForm.getField("ins_dob_mm").setValue(insuredDOB[1]);
        cmsForm.getField("ins_dob_yy").setValue(insuredDOB[2]);
        cmsForm.getField("other_ins_name").setValue("");
        cmsForm.getField("other_ins_policy").setValue("");

    }
}
