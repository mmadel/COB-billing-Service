package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PhysicianCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(String doctorName) {
        cmsForm.getField("physician_signature").setValue(doctorName);
        cmsForm.getField("physician_date").setValue(new SimpleDateFormat("dd/MM/yy").format(new Date()));
    }
}
