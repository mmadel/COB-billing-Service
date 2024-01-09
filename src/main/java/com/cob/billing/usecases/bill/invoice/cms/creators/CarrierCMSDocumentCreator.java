package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.bill.payer.PayerEntity;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

@Component
public class CarrierCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(PayerEntity payer, String primaryId) {
        cmsForm.getField("insurance_name").setValue(payer.getName());
        cmsForm.getField("insurance_address").setValue(payer.getAddress().getAddress());
        cmsForm.getField("insurance_address2").setValue("");
        cmsForm.getField("insurance_city_state_zip").setValue(payer.getAddress().getCity()
                + "," + payer.getAddress().getState() + " " + payer.getAddress().getZipCode());
        cmsForm.getField("insurance_type").setValue("Group", false);
        cmsForm.getField("insurance_id").setValue(primaryId);
    }
}
