package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.CarrierInformationModel;

public class CarrierCMSDocumentInformation extends CMSDocument{
    PatientInsuranceEntity insuranceCompany;
    CarrierInformationModel carrierInformationModel;
    @Override
    public void create() {
        carrierInformationModel = CarrierInformationModel.builder()
                .insurance_name(insuranceCompany.getPatientInsurancePolicy().getPayerName())
                .insurance_address(insuranceCompany.getPayerAddress().getAddress())
                .insurance_address2("")
                .insurance_type("Group")//Ask??
                .insurance_id(insuranceCompany.getPatientInsurancePolicy().getPrimaryId())
                .insurance_city_state_zip(insuranceCompany.getPayerAddress().getCity()
                        + "," +insuranceCompany.getPayerAddress().getState() +" " + insuranceCompany.getPayerAddress().getZipCode())
                .build();

    }
    public void setInsuranceCompany(PatientInsuranceEntity insuranceCompany){
        this.insuranceCompany = insuranceCompany;
    }
    public CarrierInformationModel getModel(){
        return this.carrierInformationModel;
    }
}
