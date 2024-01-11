package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.usecases.bill.invoice.cms.creators.*;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FillCMSDocumentUseCase {
    public PdfAcroForm cmsForm;
    @Autowired
    private CarrierCMSDocumentCreator carrierCMSDocumentCreator;
    @Autowired
    private PatientCMSDocumentCreator patientCMSDocumentCreator;
    @Autowired
    private InsuredCMSDocumentCreator insuredCMSDocumentCreator;
    @Autowired
    private ServiceLineCMSDocumentCreator serviceLineCMSDocumentCreator;
    @Autowired
    private ProviderCMSDocumentCreator providerCMSDocumentCreator;
    @Autowired
    private LocationCMSDocumentCreator locationCMSDocumentCreator;
    @Autowired
    private PhysicianCMSDocumentCreator physicianCMSDocumentCreator;
    @Autowired
    private ConfigurableCMSDocument configurableCMSDocument;

    public void fill(PatientInsuranceEntity patientInsuranceCompany
            , List<PatientInvoiceEntity> patientInvoices
            , PatientEntity patient
            , InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration) {
        this.cmsForm = cmsForm;
        fillCarrierCMSPart(patientInsuranceCompany);
        fillPatientPart(patient);
        fillInsuredPart(patientInsuranceCompany);
        fillServiceLinesPart(patientInvoices);
        fillPhysicianPart(patientInvoices.get(0).getPatientSession().getDoctorInfo());
        fillConfigurablePart(insuranceCompanyConfiguration, ""
                , patientInsuranceCompany.getPatientInsurancePolicy().getPrimaryId()
                , patient.getSsn()
                , patient.getExternalId());
        customizeTemplate();
    }

    private void fillCarrierCMSPart(PatientInsuranceEntity patientInsuranceCompany) {
        carrierCMSDocumentCreator.cmsForm = cmsForm;
        carrierCMSDocumentCreator.create(patientInsuranceCompany);
    }

    private void fillPatientPart(PatientEntity patient) {
        patientCMSDocumentCreator.cmsForm = cmsForm;
        patientCMSDocumentCreator.create(patient);
    }

    private void fillInsuredPart(PatientInsuranceEntity patientInsuranceCompany) {
        insuredCMSDocumentCreator.cmsForm = cmsForm;
        insuredCMSDocumentCreator.create(patientInsuranceCompany);
    }

    private void fillServiceLinesPart(List<PatientInvoiceEntity> patientInvoices) {
        serviceLineCMSDocumentCreator.cmsForm = cmsForm;
        serviceLineCMSDocumentCreator.create(patientInvoices);
    }

    private void fillPhysicianPart(DoctorInfo doctorInfo) {
        physicianCMSDocumentCreator.cmsForm = cmsForm;
        String doctorName = doctorInfo.getDoctorLastName()
                + ", " + doctorInfo.getDoctorFirstName();
        physicianCMSDocumentCreator.create(doctorName);
    }

    private void fillConfigurablePart(InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration
            , String clinicName
            , String primaryId
            , String patientSSN
            , String patientExternalID) {
        configurableCMSDocument.cmsForm = cmsForm;

        configurableCMSDocument.create(insuranceCompanyConfiguration, clinicName, primaryId, patientSSN, patientExternalID);
    }

    private void customizeTemplate() {
        cmsForm.removeField("Clear Form");
        cmsForm.flattenFields();
    }
}
