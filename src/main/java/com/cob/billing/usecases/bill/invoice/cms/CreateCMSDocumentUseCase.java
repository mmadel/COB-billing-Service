package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.admin.ClinicRepository;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.usecases.bill.invoice.cms.creators.*;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CreateCMSDocumentUseCase {

    @Autowired
    ResourceLoader resourceLoader;
    PdfReader cmsTemplate;
    PdfAcroForm cmsForm;
    PdfDocument cmsFile;

    List<PatientInvoiceEntity> patientInvoices;
    PatientEntity patient;
    Object[] invoicedInsuranceCompany;
    PatientInsuranceEntity patientInsuranceCompany;
    InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration;

    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    PayerRepository payerRepository;
    @Autowired
    CarrierCMSDocumentCreator carrierCMSDocumentCreator;
    @Autowired
    PatientCMSDocumentCreator patientCMSDocumentCreator;
    @Autowired
    InsuredCMSDocumentCreator insuredCMSDocumentCreator;
    @Autowired
    ServiceLineCMSDocumentCreator serviceLineCMSDocumentCreator;
    @Autowired
    ProviderCMSDocumentCreator providerCMSDocumentCreator;
    @Autowired
    LocationCMSDocumentCreator locationCMSDocumentCreator;
    @Autowired
    PhysicianCMSDocumentCreator physicianCMSDocumentCreator;

    public void create(List<PatientInvoiceEntity> patientInvoices, PatientEntity patient, Object[] invoicedInsuranceCompany, HttpServletResponse response) throws IOException {
        this.patientInvoices = patientInvoices;
        this.patient = patient;
        this.invoicedInsuranceCompany = invoicedInsuranceCompany;
        catchPatientInsuranceCompany();
        readCMSTemplate();
        createCMSFile(response);
        fillCMSDocument();
        closeCMSDocument();
    }

    private void readCMSTemplate() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:form-cms1500.pdf");
        cmsTemplate = new PdfReader(resource.getFilename());
    }

    private void createCMSFile(HttpServletResponse response) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        cmsFile = new PdfDocument(cmsTemplate, pdfWriter);
        cmsFile.removePage(2);
        cmsForm = PdfAcroForm.getAcroForm(cmsFile, true);
    }

    private void fillCMSDocument() {
        fillCarrierCMSPart();
        fillPatientPart();
        fillPatientPart();
        fillInsuredPart();
        fillServiceLinesPart();
        fillPhysicianPart();
        fillBasedOnInsuranceCompanyConfiguration();
        cmsForm.removeField("Clear Form");
        cmsForm.flattenFields();
    }

    private void closeCMSDocument() throws IOException {
        cmsFile.close();
        cmsTemplate.close();
    }


    private void fillCarrierCMSPart() {
        carrierCMSDocumentCreator.cmsForm = cmsForm;
        carrierCMSDocumentCreator.create(patientInsuranceCompany);
    }

    private void fillPatientPart() {
        patientCMSDocumentCreator.cmsForm = cmsForm;
        patientCMSDocumentCreator.create(patient);
    }

    private void fillInsuredPart() {
        insuredCMSDocumentCreator.cmsForm = cmsForm;
        insuredCMSDocumentCreator.create(patientInsuranceCompany);
    }

    private void fillServiceLinesPart() {
        serviceLineCMSDocumentCreator.cmsForm = cmsForm;
        serviceLineCMSDocumentCreator.create(patientInvoices);
    }

    private void fillPhysicianPart() {
        physicianCMSDocumentCreator.cmsForm = cmsForm;
        String doctorName = patientInvoices.get(0).getPatientSession().getDoctorInfo().getDoctorLastName()
                + ", " + patientInvoices.get(0).getPatientSession().getDoctorInfo().getDoctorFirstName();
        physicianCMSDocumentCreator.create(doctorName);
    }

    private void fillBasedOnInsuranceCompanyConfiguration() {
        OrganizationEntity organization = null;

        if (insuranceCompanyConfiguration.getBox33() == -1L)
            organization = organizationRepository.findByType(OrganizationType.Default).get();
        else
            organization = organizationRepository.findById(insuranceCompanyConfiguration.getBox33()).get();
        if (organization != null) {
            providerCMSDocumentCreator.cmsForm = cmsForm;
            providerCMSDocumentCreator.create(organization);
        }
        if (insuranceCompanyConfiguration.getBox32()) {
            String title = patientInvoices.get(0).getPatientSession().getClinicInfo().getClinicName();
            ClinicEntity clinic = clinicRepository.findByTitle(title).get();
            if (clinic != null) {
                locationCMSDocumentCreator.cmsForm = cmsForm;
                locationCMSDocumentCreator.create(clinic);
            }
        }
        if (insuranceCompanyConfiguration.getBox26().equals("insured_primary_id"))
            cmsForm.getField("pt_account").setValue(patientInsuranceCompany.getPatientInsurancePolicy().getPrimaryId());
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_ssn"))
            cmsForm.getField("pt_account").setValue(patient.getSsn());
        if (insuranceCompanyConfiguration.getBox26().equals("pateint_external_id"))
            cmsForm.getField("pt_account").setValue(patient.getExternalId());

    }

    private void catchPatientInsuranceCompany() {
        String name = (String) invoicedInsuranceCompany[0];
        InsuranceCompanyVisibility visibility = (InsuranceCompanyVisibility) invoicedInsuranceCompany[1];
        patientInsuranceCompany = null;
        patient.getInsurances().stream()
                .forEach(patientInsurance -> {
                    if (patientInsuranceCompany == null)
                        switch (visibility) {
                            case Internal:
                                if (patientInsurance.getPatientInsuranceInternalCompany() != null
                                        && patientInsurance.getPatientInsuranceInternalCompany().getInsuranceCompany().getName().equals(name)) {
                                    patientInsuranceCompany = patientInsurance;
                                    insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository
                                            .findByInternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceInternalCompany()
                                                    .getInsuranceCompany().getId()).get();
                                }
                                break;
                            case External:
                                if (patientInsurance.getPatientInsuranceExternalCompany() != null
                                        && patientInsurance.getPatientInsuranceExternalCompany().getInsuranceCompany().getName().equals(name)) {
                                    patientInsuranceCompany = patientInsurance;
                                    insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository
                                            .findByExternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceExternalCompany()
                                                    .getInsuranceCompany().getId()).get();
                                }
                                break;
                        }
                });
    }
}
