package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.usecases.bill.invoice.cms.creators.CarrierCMSDocumentInformation;
import com.cob.billing.usecases.bill.invoice.cms.creators.InsuredCMSDocumentInformation;
import com.cob.billing.usecases.bill.invoice.cms.creators.PatientCMSDocumentInformation;
import com.cob.billing.usecases.bill.invoice.cms.models.CMSDocumentModel;
import com.cob.billing.usecases.bill.invoice.cms.models.CarrierInformationModel;
import com.cob.billing.usecases.bill.invoice.cms.models.InsuredInformationModel;
import com.cob.billing.usecases.bill.invoice.cms.models.PatientInformationModel;
import org.springframework.stereotype.Component;

@Component
public class GenerateCMSModelUseCase {
    public CMSDocumentModel generate(PatientEntity patient, Long insuranceCompanyId) {

        return CMSDocumentModel.builder()
                .carrierInformationModel(generateCarrierCMSDocumentInformation(patient, insuranceCompanyId))
                .patientInformationModel(generatePatientInformation(patient))
                .insuredInformationModel(generateInsuredInformation(patient, insuranceCompanyId))
                .build();
    }


    private CarrierInformationModel generateCarrierCMSDocumentInformation(PatientEntity patient, Long insuranceCompanyId) {
        PatientInsuranceEntity insuranceCompany = patient.getInsurances().stream()
                .filter(patientInsuranceEntity -> patientInsuranceEntity.getInsuranceCompany().equals(insuranceCompanyId))
                .findFirst()
                .get();
        CarrierCMSDocumentInformation carrierCMSDocumentInformation = new CarrierCMSDocumentInformation();
        carrierCMSDocumentInformation.setInsuranceCompany(insuranceCompany);
        carrierCMSDocumentInformation.create();
        return carrierCMSDocumentInformation.getModel();
    }

    private PatientInformationModel generatePatientInformation(PatientEntity patient) {
        PatientCMSDocumentInformation patientCMSDocumentInformation = new PatientCMSDocumentInformation();
        patientCMSDocumentInformation.setPatient(patient);
        patientCMSDocumentInformation.create();
        return patientCMSDocumentInformation.getModel();
    }

    private InsuredInformationModel generateInsuredInformation(PatientEntity patient, Long insuranceCompanyId) {
        PatientInsuranceEntity insuranceCompany = patient.getInsurances().stream()
                .filter(patientInsuranceEntity -> patientInsuranceEntity.getInsuranceCompany().equals(insuranceCompanyId))
                .findFirst()
                .get();
        InsuredCMSDocumentInformation insuredCMSDocumentInformation = new InsuredCMSDocumentInformation();
        insuredCMSDocumentInformation.setInsured(insuranceCompany);
        insuredCMSDocumentInformation.create();
        return insuredCMSDocumentInformation.getModel();
    }
}
