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
                .employment("NO")
                .pt_auto_accident("NO") //?
                .other_accident("NO") //?
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
