package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FillCMSDocumentUseCase {
    public PdfAcroForm cmsForm;
    @Autowired
    FillNonRepeatablePart fillNonRepeatablePart;

    public void fill(InvoiceRequest invoiceRequest, PdfAcroForm cmsForm) {
        fillNonRepeatablePart.fill(invoiceRequest,cmsForm);
        customizeTemplate();
    }

    private void customizeTemplate() {
        cmsForm.removeField("Clear Form");
        cmsForm.flattenFields();
    }
}
