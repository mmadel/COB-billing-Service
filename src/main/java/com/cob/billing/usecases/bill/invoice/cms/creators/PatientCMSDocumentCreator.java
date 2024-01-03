package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class PatientCMSDocumentCreator {
    public PdfAcroForm cmsForm;
    public PatientEntity patient;

    public void create(PatientEntity patient) {
        this.patient = patient;
        fillBasicInfo();
        fillAdvancedInfo();
    }

    private void fillBasicInfo() {
        String[] patientDOB = DateConstructor.construct(patient.getBirthDate());
        cmsForm.getField("pt_name").setValue(patient.getLastName() + "," + patient.getFirstName());
        cmsForm.getField("birth_mm").setValue(patientDOB[0]);
        cmsForm.getField("birth_dd").setValue(patientDOB[1]);
        cmsForm.getField("birth_yy").setValue(patientDOB[2]);
        cmsForm.getField("sex").setValue(patient.getGender().name().equals("Male") ? "M" : "F", false);
        cmsForm.getField("pt_street").setValue(patient.getAddress().getFirst());
        cmsForm.getField("pt_city").setValue(patient.getAddress().getCity());
        cmsForm.getField("pt_state").setValue(patient.getAddress().getState());
        cmsForm.getField("pt_zip").setValue(patient.getAddress().getZipCode());
        cmsForm.getField("pt_AreaCode").setValue(patient.getPhone().substring(1, 4));
        cmsForm.getField("pt_phone").setValue(patient.getPhone()
                .substring(5, patient.getPhone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
    }

    private void fillAdvancedInfo() {
        String[] unableToWorkDateStartDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getUnableToWorkStartDate());
        String[] unableToWorkDateEndDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getUnableToWorkEndDate());

        String[] hospitalizedStartDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getHospitalizedStartDate());
        String[] hospitalizedEndDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getHospitalizedEndDate());

        cmsForm.getField("employment").setValue(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment() ? "YES" : "NO", false);
        cmsForm.getField("pt_auto_accident").setValue(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident() ? "YES" : "NO", false);
        cmsForm.getField("other_accident").setValue(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isOtherAccident() ? "YES" : "NO", false);
        cmsForm.getField("accident_place").setValue("");

        cmsForm.getField("work_mm_from").setValue(unableToWorkDateStartDate[0]);
        cmsForm.getField("work_dd_from").setValue(unableToWorkDateStartDate[1]);
        cmsForm.getField("work_yy_from").setValue(unableToWorkDateStartDate[2]);

        cmsForm.getField("work_mm_end").setValue(unableToWorkDateEndDate[0]);
        cmsForm.getField("work_dd_end").setValue(unableToWorkDateEndDate[1]);
        cmsForm.getField("work_yy_end").setValue(unableToWorkDateEndDate[2]);

        cmsForm.getField("hosp_mm_from").setValue(hospitalizedStartDate[0]);
        cmsForm.getField("hosp_dd_from").setValue(hospitalizedStartDate[1]);
        cmsForm.getField("hosp_yy_from").setValue(hospitalizedStartDate[2]);

        cmsForm.getField("hosp_mm_end").setValue(hospitalizedEndDate[0]);
        cmsForm.getField("hosp_dd_end").setValue(hospitalizedEndDate[1]);
        cmsForm.getField("hosp_yy_end").setValue(hospitalizedEndDate[2]);
    }
    private void fillPatientAccount(){
        cmsForm.getField("").setValue("");
    }
}
