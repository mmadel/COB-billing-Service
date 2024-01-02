package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.CMSDocumentModel;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class CreateCMS1500DocumentUseCase {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    GenerateCMSModelUseCase generateCMSModelUseCase;
    //AcroFields cmsFields;
    CMSDocumentModel cmsDocumentModel;
    PdfAcroForm cmsForm;

    public void create(List<PatientInvoiceEntity> patientInvoices) throws IOException {
        PatientEntity patient = patientInvoices.get(0).getPatient();
        Long insuranceCompanyId = patientInvoices.get(0).getInsuranceCompany();
        cmsDocumentModel = generateCMSModelUseCase.generate(patient, insuranceCompanyId);
        fillCMS();
    }

    private void fillCMS() throws IOException {
        try {

            Resource resource = resourceLoader.getResource("classpath:form-cms1500.pdf");
            PdfReader reader = new PdfReader(resource.getFilename());


            PdfDocument existingPdf = new PdfDocument(reader, new PdfWriter("filled-form.pdf"));
            cmsForm = PdfAcroForm.getAcroForm(existingPdf, true);
            fillCarrierCMSDocumentInformation();
            fillPatientInformation();
            fillInsuredInformation();
            cmsForm.flattenFields();
            existingPdf.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillCarrierCMSDocumentInformation() {
        cmsForm.getField("insurance_name").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_name());
        cmsForm.getField("insurance_address").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_address());
        cmsForm.getField("insurance_address2").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_address2());
        cmsForm.getField("insurance_city_state_zip").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_city_state_zip());
        cmsForm.getField("insurance_type").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_type());
        cmsForm.getField("insurance_id").setValue(cmsDocumentModel.getCarrierInformationModel().getInsurance_id());
    }

    private void fillPatientInformation() {
        cmsForm.getField("pt_name").setValue(cmsDocumentModel.getPatientInformationModel().getPt_name());
        cmsForm.getField("birth_mm").setValue(cmsDocumentModel.getPatientInformationModel().getBirth_mm());
        cmsForm.getField("birth_dd").setValue(cmsDocumentModel.getPatientInformationModel().getBirth_dd());
        cmsForm.getField("birth_yy").setValue(cmsDocumentModel.getPatientInformationModel().getBirth_yy());
        cmsForm.getField("sex").setValue(cmsDocumentModel.getPatientInformationModel().getSex(),false);
        cmsForm.getField("pt_street").setValue(cmsDocumentModel.getPatientInformationModel().getPt_street());
        cmsForm.getField("pt_city").setValue(cmsDocumentModel.getPatientInformationModel().getPt_city());
        cmsForm.getField("pt_state").setValue(cmsDocumentModel.getPatientInformationModel().getPt_state());
        cmsForm.getField("pt_zip").setValue(cmsDocumentModel.getPatientInformationModel().getPt_zip());
        cmsForm.getField("pt_phone").setValue(cmsDocumentModel.getPatientInformationModel().getPt_phone());
        cmsForm.getField("employment").setValue(cmsDocumentModel.getPatientInformationModel().getEmployment(),false);
        cmsForm.getField("pt_auto_accident").setValue(cmsDocumentModel.getPatientInformationModel().getPt_auto_accident(),false);
        cmsForm.getField("other_accident").setValue(cmsDocumentModel.getPatientInformationModel().getOther_accident(),false);
        cmsForm.getField("accident_place").setValue(cmsDocumentModel.getPatientInformationModel().getAccident_place());
    }

    private void fillInsuredInformation() {
        cmsForm.getField("ins_name").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_name());
        cmsForm.getField("rel_to_ins").setValue(cmsDocumentModel.getInsuredInformationModel().getRel_to_ins());
        cmsForm.getField("ins_street").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_street());
        cmsForm.getField("ins_city").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_city());
        cmsForm.getField("ins_state").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_state());
        cmsForm.getField("ins_zip").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_zip());
        cmsForm.getField("ins_phone").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_phone());
        cmsForm.getField("ins_policy").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_policy());
        cmsForm.getField("ins_sex").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_sex(),false);
        cmsForm.getField("ins_dob_dd").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_dd());
        cmsForm.getField("ins_dob_mm").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_mm());
        cmsForm.getField("ins_dob_yy").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_yy());
        cmsForm.getField("other_ins_name").setValue(cmsDocumentModel.getInsuredInformationModel().getOther_ins_name());
        cmsForm.getField("other_ins_policy").setValue(cmsDocumentModel.getInsuredInformationModel().getOther_ins_policy());
    }

}
