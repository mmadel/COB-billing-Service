package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.model.admin.clinic.Clinic;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class LocationCMSDocumentCreator {
    public void create(Clinic clinic, PdfAcroForm cmsForm) {
        cmsForm.getField("fac_name").setValue(clinic.getTitle());
        cmsForm.getField("fac_street").setValue(clinic.getClinicdata().getAddress());
        cmsForm.getField("fac_location").setValue(clinic.getClinicdata().getCity()
                + ", " + clinic.getClinicdata().getState()
                + " " + clinic.getClinicdata().getZipCode());
    }
}
