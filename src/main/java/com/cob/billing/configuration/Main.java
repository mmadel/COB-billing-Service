package com.cob.billing.configuration;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        File ddd = new File("C:\\cob\\documents\\billing\\form-cms1500.pdf");
        PdfReader reader = new PdfReader(ddd);


        PdfDocument existingPdf = new PdfDocument(reader, new PdfWriter("filled-form.pdf"));

        PdfAcroForm cmsForm = PdfAcroForm.getAcroForm(existingPdf, true);
        for (Map.Entry<String, PdfFormField> entry : cmsForm.getAllFormFields().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getValueAsString());
        }
        cmsForm.flattenFields();
        existingPdf.close();
        reader.close();

    }

}
