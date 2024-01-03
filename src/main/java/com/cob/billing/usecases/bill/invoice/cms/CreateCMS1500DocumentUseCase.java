package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.CMSDocumentModel;
import com.cob.billing.usecases.bill.invoice.cms.models.ServiceLineModel;
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
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CreateCMS1500DocumentUseCase {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    GenerateCMSModelUseCase generateCMSModelUseCase;
    //AcroFields cmsFields;
    CMSDocumentModel cmsDocumentModel;
    PdfAcroForm cmsForm;

    private void fillCMS(HttpServletResponse response) throws IOException {
        try {

            Resource resource = resourceLoader.getResource("classpath:form-cms1500.pdf");
            PdfReader reader = new PdfReader(resource.getFilename());

            PdfWriter dd = new PdfWriter(response.getOutputStream());
            PdfDocument existingPdf = new PdfDocument(reader, dd);
            cmsForm = PdfAcroForm.getAcroForm(existingPdf, true);
            fillCarrierCMSDocumentInformation();
            fillPatientInformation();
            fillInsuredInformation();
            fillServiceLines();
            cmsForm.flattenFields();

            existingPdf.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(List<PatientInvoiceEntity> patientInvoices, HttpServletResponse response) throws IOException {
        PatientEntity patient = patientInvoices.get(0).getPatient();
        Long insuranceCompanyId = patientInvoices.get(0).getInsuranceCompany();
        cmsDocumentModel = generateCMSModelUseCase.generate(patient, insuranceCompanyId, patientInvoices);
        fillCMS(response);
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
        cmsForm.getField("sex").setValue(cmsDocumentModel.getPatientInformationModel().getSex(), false);
        cmsForm.getField("pt_street").setValue(cmsDocumentModel.getPatientInformationModel().getPt_street());
        cmsForm.getField("pt_city").setValue(cmsDocumentModel.getPatientInformationModel().getPt_city());
        cmsForm.getField("pt_state").setValue(cmsDocumentModel.getPatientInformationModel().getPt_state());
        cmsForm.getField("pt_zip").setValue(cmsDocumentModel.getPatientInformationModel().getPt_zip());
        cmsForm.getField("pt_AreaCode").setValue(cmsDocumentModel.getPatientInformationModel().getPt_phone().substring(1, 4));
        cmsForm.getField("pt_phone").setValue(cmsDocumentModel.getPatientInformationModel().getPt_phone()
                .substring(5, cmsDocumentModel.getPatientInformationModel().getPt_phone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("employment").setValue(cmsDocumentModel.getPatientInformationModel().getEmployment(), false);
        cmsForm.getField("pt_auto_accident").setValue(cmsDocumentModel.getPatientInformationModel().getPt_auto_accident(), false);
        cmsForm.getField("other_accident").setValue(cmsDocumentModel.getPatientInformationModel().getOther_accident(), false);
        cmsForm.getField("accident_place").setValue(cmsDocumentModel.getPatientInformationModel().getAccident_place());

        cmsForm.getField("work_mm_from").setValue(cmsDocumentModel.getPatientInformationModel().getWork_mm_from());
        cmsForm.getField("work_dd_from").setValue(cmsDocumentModel.getPatientInformationModel().getWork_dd_from());
        cmsForm.getField("work_yy_from").setValue(cmsDocumentModel.getPatientInformationModel().getWork_yy_from());

        cmsForm.getField("work_mm_end").setValue(cmsDocumentModel.getPatientInformationModel().getWork_mm_end());
        cmsForm.getField("work_dd_end").setValue(cmsDocumentModel.getPatientInformationModel().getWork_dd_end());
        cmsForm.getField("work_yy_end").setValue(cmsDocumentModel.getPatientInformationModel().getWork_yy_end());

        cmsForm.getField("hosp_mm_from").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_mm_from());
        cmsForm.getField("hosp_dd_from").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_dd_from());
        cmsForm.getField("hosp_yy_from").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_yy_from());

        cmsForm.getField("hosp_mm_end").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_mm_end());
        cmsForm.getField("hosp_dd_end").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_dd_end());
        cmsForm.getField("hosp_yy_end").setValue(cmsDocumentModel.getPatientInformationModel().getHosp_yy_end());
    }

    private void fillInsuredInformation() {
        cmsForm.getField("ins_name").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_name());
        cmsForm.getField("rel_to_ins").setValue(cmsDocumentModel.getInsuredInformationModel().getRel_to_ins(), false);
        cmsForm.getField("ins_street").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_street());
        cmsForm.getField("ins_city").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_city());
        cmsForm.getField("ins_state").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_state());
        cmsForm.getField("ins_zip").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_zip());
        cmsForm.getField("ins_phone area").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_phone().substring(1, 4));
        cmsForm.getField("ins_phone").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_phone()
                .substring(5, cmsDocumentModel.getInsuredInformationModel().getIns_phone().length())
                .replace("(", "")
                .replace(")", "")
                .replace("-", ""));
        cmsForm.getField("ins_policy").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_policy());
        cmsForm.getField("ins_sex").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_sex(), false);
        cmsForm.getField("ins_dob_dd").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_dd());
        cmsForm.getField("ins_dob_mm").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_mm());
        cmsForm.getField("ins_dob_yy").setValue(cmsDocumentModel.getInsuredInformationModel().getIns_dob_yy());
        cmsForm.getField("other_ins_name").setValue(cmsDocumentModel.getInsuredInformationModel().getOther_ins_name());
        cmsForm.getField("other_ins_policy").setValue(cmsDocumentModel.getInsuredInformationModel().getOther_ins_policy());
    }

    private void fillServiceLines() {
        int counter = 1;
        float totalCharge = 0.0f;

        for (ServiceLineModel serviceLineModel : cmsDocumentModel.getServiceLines()) {
            cmsForm.getField("sv" + counter + "_mm_from").setValue(serviceLineModel.getSv_mm_from());
            cmsForm.getField("sv" + counter + "_dd_from").setValue(serviceLineModel.getSv_dd_from());
            cmsForm.getField("sv" + counter + "_yy_from").setValue(serviceLineModel.getSv_yy_from());

            cmsForm.getField("sv" + counter + "_mm_end").setValue(serviceLineModel.getSv_mm_end());
            cmsForm.getField("sv" + counter + "_dd_end").setValue(serviceLineModel.getSv_dd_end());
            cmsForm.getField("sv" + counter + "_yy_end").setValue(serviceLineModel.getSv_dd_end());

            cmsForm.getField("ch" + counter).setValue(serviceLineModel.getCh());
            cmsForm.getField("day" + counter).setValue(serviceLineModel.getDay());

            cmsForm.getField("place" + counter).setValue(serviceLineModel.getPlace().split("_")[1]);
            cmsForm.getField("cpt" + counter).setValue(serviceLineModel.getCpt());
            cmsForm.getField("mod" + counter).setValue(serviceLineModel.getMod()[0]);
            cmsForm.getField("mod" + counter + "a").setValue(serviceLineModel.getMod()[1]);
            cmsForm.getField("mod" + counter + "b").setValue(serviceLineModel.getMod()[2]);
            cmsForm.getField("mod" + counter + "c").setValue(serviceLineModel.getMod()[3]);
            cmsForm.getField("local" + counter).setValue(serviceLineModel.getLocal());
            counter = counter + 1;
            totalCharge = totalCharge + Float.parseFloat(serviceLineModel.getCh());
            ;
        }
        cmsForm.getField("t_charge").setValue(String.valueOf(totalCharge));
    }
}
