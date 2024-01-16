package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PhysicianCMSDocumentCreator {

    public void create(DoctorInfo doctorInfo ,PdfAcroForm cmsForm) {
        cmsForm.getField("physician_signature").setValue(doctorInfo.getDoctorLastName() +","+doctorInfo.getDoctorFirstName());
        cmsForm.getField("physician_date").setValue(new SimpleDateFormat("dd/MM/yy").format(new Date()));
    }
}
