package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLineCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(List<PatientInvoiceEntity> patientInvoices) {
        int counter = 1;
        float totalCharge = 0.0f;

        for (PatientInvoiceEntity patientInvoice : patientInvoices) {
            String[] dateOfService = DateConstructor.construct(patientInvoice.getPatientSession().getServiceDate());
            cmsForm.getField("sv" + counter + "_mm_from").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_from").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_from").setValue(dateOfService[2]);

            cmsForm.getField("sv" + counter + "_mm_end").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_end").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_end").setValue(dateOfService[2]);

            cmsForm.getField("ch" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getCharge().toString());
            cmsForm.getField("day" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getUnit().toString());

            cmsForm.getField("place" + counter).setValue(patientInvoice.getPatientSession().getPlaceOfCode().split("_")[1]);
            cmsForm.getField("cpt" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getServiceCode());
            cmsForm.getField("mod" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.")[0]);
            cmsForm.getField("mod" + counter + "a").setValue(patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.")[1]);
            cmsForm.getField("mod" + counter + "b").setValue(patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.")[2]);
            cmsForm.getField("mod" + counter + "c").setValue(patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.")[3]);
            cmsForm.getField("local" + counter).setValue(patientInvoice.getPatientSession().getDoctorInfo().getDoctorNPI());
            counter = counter + 1;
            totalCharge = totalCharge + patientInvoice.getServiceLine().getCptCode().getCharge();
            ;
        }
        cmsForm.getField("t_charge").setValue(String.valueOf(totalCharge));
    }
}
