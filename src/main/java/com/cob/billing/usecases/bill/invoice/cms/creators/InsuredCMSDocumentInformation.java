package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.InsuredInformationModel;
import com.cob.billing.util.DateConstructor;

public class InsuredCMSDocumentInformation extends CMSDocument {
    private PatientInsuranceEntity insuranceCompany;
    private InsuredInformationModel insuredInformationModel;

    @Override
    public void create() {
        String[] insuredDOB = DateConstructor.construct(insuranceCompany.getPatientRelation().getR_birthDate());
        insuredInformationModel = InsuredInformationModel.builder()
                .ins_name(insuranceCompany.getPatientRelation().getR_lastName() + "," + insuranceCompany.getPatientRelation().getR_firstName())
                .rel_to_ins(insuranceCompany.getRelation())
                .ins_street(insuranceCompany.getPatientRelation().getR_address().getFirst())
                .ins_city(insuranceCompany.getPatientRelation().getR_address().getCity())
                .ins_state(insuranceCompany.getPatientRelation().getR_address().getState())
                .ins_zip(insuranceCompany.getPatientRelation().getR_address().getZipCode())
                .ins_phone(insuranceCompany.getPatientRelation().getR_phone())
                .ins_sex(insuranceCompany.getPatientRelation().getR_gender().equals("Male") ? "MALE" : "FEMALE")
                .ins_dob_mm(insuredDOB[0])
                .ins_dob_dd(insuredDOB[1])
                .ins_dob_yy(insuredDOB[2])
                .other_ins_name("") //?
                .other_ins_policy("") //?
                .build();
    }

    public void setInsured(PatientInsuranceEntity insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public InsuredInformationModel getModel() {
        return this.insuredInformationModel;
    }
}
