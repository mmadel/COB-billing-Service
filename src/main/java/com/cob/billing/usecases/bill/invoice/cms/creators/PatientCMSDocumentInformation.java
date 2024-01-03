package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.PatientInformationModel;
import com.cob.billing.util.DateConstructor;

public class PatientCMSDocumentInformation extends CMSDocument {
    private PatientEntity patient;
    private PatientInformationModel patientInformationModel;

    @Override
    public void create() {
        String[] patientDOB = DateConstructor.construct(patient.getBirthDate());
        String[] unableToWorkDateStartDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getUnableToWorkStartDate());
        String[] unableToWorkDateEndDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getUnableToWorkEndDate());

        String[] hospitalizedStartDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getHospitalizedStartDate());
        String[] hospitalizedEndDate = DateConstructor.construct(patient.getPatientAdvancedInformation().getHospitalizedEndDate());
        patientInformationModel = PatientInformationModel
                .builder()
                .pt_name(patient.getLastName() + "," + patient.getFirstName())
                .birth_mm(patientDOB[0])
                .birth_dd(patientDOB[1])
                .birth_yy(patientDOB[2])
                .sex(patient.getGender().name().equals("Male") ? "M" : "F")
                .pt_street(patient.getAddress().getFirst())
                .pt_city(patient.getAddress().getCity())
                .pt_state(patient.getAddress().getState())
                .pt_zip(patient.getAddress().getZipCode())
                .pt_phone(patient.getPhone())
                .work_mm_from(unableToWorkDateStartDate[0])
                .work_dd_from(unableToWorkDateStartDate[1])
                .work_yy_from(unableToWorkDateStartDate[2])
                .work_mm_end(unableToWorkDateEndDate[0])
                .work_dd_end(unableToWorkDateEndDate[1])
                .work_yy_end(unableToWorkDateEndDate[2])
                .hosp_mm_from(hospitalizedStartDate[0])
                .hosp_dd_from(hospitalizedStartDate[1])
                .hosp_yy_from(hospitalizedStartDate[2])
                .hosp_mm_end(hospitalizedEndDate[0])
                .hosp_dd_end(hospitalizedEndDate[1])
                .hosp_yy_end(hospitalizedEndDate[2])
                .employment(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment()?"YES":"NO")
                .pt_auto_accident(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident()?"YES":"NO")
                .other_accident(patient.getPatientAdvancedInformation().getPateintAdvancedCondtion().isOtherAccident()?"YES":"NO") //?
                .accident_place("") //?
                .build();
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public PatientInformationModel getModel() {
        return this.patientInformationModel;
    }

}
