package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.CMSDocumentModel;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class CreateCMS1500DocumentUseCase {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    GenerateCMSModelUseCase generateCMSModelUseCase;
    AcroFields cmsFields;
    CMSDocumentModel cmsDocumentModel;

    public void create(List<PatientInvoiceEntity> patientInvoices) throws DocumentException, IOException {
        PatientEntity patient = patientInvoices.get(0).getPatient();
        Long insuranceCompanyId = patientInvoices.get(0).getInsuranceCompany();
        cmsDocumentModel = generateCMSModelUseCase.generate(patient, insuranceCompanyId);
        fillCMS();
    }

    private void fillCMS() throws IOException, DocumentException {
        Resource resource=resourceLoader.getResource("classpath:form-cms1500.pdf");
        PdfReader reader = new PdfReader(resource.getFilename());
        PdfCopyFields copy = new PdfCopyFields(new FileOutputStream("unlocked-form.pdf"));
        copy.addDocument(reader);
        copy.close();
        reader.close();
        reader = new PdfReader("unlocked-form.pdf");
        AcroFields fields = reader.getAcroFields();
        reader.close();
        PdfReader unlockedReader = new PdfReader("unlocked-form.pdf");
        PdfStamper stamper = new PdfStamper(unlockedReader, new FileOutputStream("filled-form.pdf"));
        cmsFields = stamper.getAcroFields();
        fillCarrierCMSDocumentInformation();
        fillPatientInformation();
        fillInsuredInformation();
        stamper.close();
    }

    private void fillCarrierCMSDocumentInformation() throws DocumentException, IOException {
        cmsFields.setField("insurance_name", cmsDocumentModel.getCarrierInformationModel().getInsurance_name());
        cmsFields.setField("insurance_address", cmsDocumentModel.getCarrierInformationModel().getInsurance_address());
        cmsFields.setField("insurance_address2", cmsDocumentModel.getCarrierInformationModel().getInsurance_address2());
        cmsFields.setField("insurance_city_state_zip", cmsDocumentModel.getCarrierInformationModel().getInsurance_city_state_zip());
        cmsFields.setField("insurance_type", cmsDocumentModel.getCarrierInformationModel().getInsurance_type());
        cmsFields.setField("insurance_id", cmsDocumentModel.getCarrierInformationModel().getInsurance_id());

    }

    private void fillPatientInformation() throws DocumentException, IOException {
        cmsFields.setField("pt_name", cmsDocumentModel.getPatientInformationModel().getPt_name());
        cmsFields.setField("birth_mm", cmsDocumentModel.getPatientInformationModel().getBirth_mm());
        cmsFields.setField("birth_dd", cmsDocumentModel.getPatientInformationModel().getBirth_dd());
        cmsFields.setField("birth_yy", cmsDocumentModel.getPatientInformationModel().getBirth_yy());
        cmsFields.setField("sex", cmsDocumentModel.getPatientInformationModel().getSex());
        cmsFields.setField("pt_street", cmsDocumentModel.getPatientInformationModel().getPt_street());
        cmsFields.setField("pt_city", cmsDocumentModel.getPatientInformationModel().getPt_city());
        cmsFields.setField("pt_state", cmsDocumentModel.getPatientInformationModel().getPt_state());

        cmsFields.setField("pt_zip", cmsDocumentModel.getPatientInformationModel().getPt_zip());
        cmsFields.setField("pt_phone", cmsDocumentModel.getPatientInformationModel().getPt_phone());
        cmsFields.setField("employment", cmsDocumentModel.getPatientInformationModel().getEmployment());
        cmsFields.setField("pt_auto_accident", cmsDocumentModel.getPatientInformationModel().getPt_auto_accident());
        cmsFields.setField("other_accident", cmsDocumentModel.getPatientInformationModel().getOther_accident());
        cmsFields.setField("accident_place", cmsDocumentModel.getPatientInformationModel().getAccident_place());
    }

    private void fillInsuredInformation() throws DocumentException, IOException {
        cmsFields.setField("ins_name", cmsDocumentModel.getInsuredInformationModel().getIns_name());
        cmsFields.setField("rel_to_ins", cmsDocumentModel.getInsuredInformationModel().getRel_to_ins());
        cmsFields.setField("ins_street", cmsDocumentModel.getInsuredInformationModel().getIns_street());
        cmsFields.setField("ins_city", cmsDocumentModel.getInsuredInformationModel().getIns_city());
        cmsFields.setField("ins_state", cmsDocumentModel.getInsuredInformationModel().getIns_state());
        cmsFields.setField("ins_zip", cmsDocumentModel.getInsuredInformationModel().getIns_zip());
        cmsFields.setField("ins_phone", cmsDocumentModel.getInsuredInformationModel().getIns_phone());
        cmsFields.setField("ins_policy", cmsDocumentModel.getInsuredInformationModel().getIns_policy());
        cmsFields.setField("ins_sex", cmsDocumentModel.getInsuredInformationModel().getIns_sex());

        cmsFields.setField("ins_dob_dd", cmsDocumentModel.getInsuredInformationModel().getIns_dob_dd());
        cmsFields.setField("ins_dob_mm", cmsDocumentModel.getInsuredInformationModel().getIns_dob_mm());
        cmsFields.setField("ins_dob_yy", cmsDocumentModel.getInsuredInformationModel().getIns_dob_yy());
        cmsFields.setField("other_ins_name", cmsDocumentModel.getInsuredInformationModel().getOther_ins_name());
        cmsFields.setField("other_ins_policy", cmsDocumentModel.getInsuredInformationModel().getOther_ins_policy());


    }

}
