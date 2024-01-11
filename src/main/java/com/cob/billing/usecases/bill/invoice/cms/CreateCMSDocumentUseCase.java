package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
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
    @Autowired
    FindPatientInsuranceByInsuranceCompanyUseCase findPatientInsuranceByInsuranceCompanyUseCase;
    @Autowired
    FillCMSDocumentUseCase fillCMSDocumentUseCase;

    PdfReader cmsTemplate;
    PdfAcroForm cmsForm;
    PdfDocument cmsFile;

    List<PatientInvoiceEntity> patientInvoices;
    PatientEntity patient;
    Object[] invoicedInsuranceCompany;
    PatientInsuranceEntity patientInsuranceCompany;
    InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration;

    public void create(List<PatientInvoiceEntity> patientInvoices
            , PatientEntity patient
            , Object[] invoicedInsuranceCompany
            , HttpServletResponse response) throws IOException {
        this.patientInvoices = patientInvoices;
        this.patient = patient;
        this.invoicedInsuranceCompany = invoicedInsuranceCompany;
        readCMSTemplate();
        createCMSFile(response);
        findInsuranceCompanyData();
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

    private void findInsuranceCompanyData() {
        findPatientInsuranceByInsuranceCompanyUseCase.find(patient.getInsurances()
                , (String) invoicedInsuranceCompany[0]
                , (InsuranceCompanyVisibility) invoicedInsuranceCompany[1]);

        insuranceCompanyConfiguration = findPatientInsuranceByInsuranceCompanyUseCase.getInsuranceCompanyConfiguration();
        patientInsuranceCompany = findPatientInsuranceByInsuranceCompanyUseCase.getPatientInsuranceCompany();
    }

    private void fillCMSDocument() {
        fillCMSDocumentUseCase.cmsForm = cmsForm;
        fillCMSDocumentUseCase.fill(patientInsuranceCompany, patientInvoices, patient, insuranceCompanyConfiguration);
        customizeTemplate();
    }

    private void customizeTemplate() {
        cmsForm.removeField("Clear Form");
        cmsForm.flattenFields();
    }

    private void closeCMSDocument() throws IOException {
        cmsFile.close();
        cmsTemplate.close();
    }

}
