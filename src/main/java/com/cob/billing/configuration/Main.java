package com.cob.billing.configuration;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.element.AreaBreak;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

public class Main {
    static File ddd = new File("C:\\cob\\documents\\billing\\form-cms1500.pdf");

    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(ddd);
        PdfDocument existingPdf = new PdfDocument(reader, new PdfWriter("filled-form.pdf"));

        PdfAcroForm cmsForm = PdfAcroForm.getAcroForm(existingPdf, true);
        existingPdf.removePage(2);
        cmsForm.getField("pt_name").setValue("mohamed ahmed");
        cmsForm.flattenFields();
//        for (Map.Entry<String, PdfFormField> entry : cmsForm.getAllFormFields().entrySet()) {
//            entry.getValue().getFontSize();
//            System.out.println(entry.getKey() + " " + entry.getValue().getDisplayValue());
//        }
        createNewDoc();
        existingPdf.close();
        reader.close();

        PdfDocument pdf = new PdfDocument(new PdfWriter("final.pdf"));
        PdfMerger merger = new PdfMerger(pdf);

        PdfReader source = new PdfReader("filled-form.pdf");
        PdfDocument sourceDoc = new PdfDocument(source);
        merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());

        PdfReader dest = new PdfReader("tmp.pdf");
        PdfDocument destDoc = new PdfDocument(dest);
        merger.merge(destDoc, 1, destDoc.getNumberOfPages());
        sourceDoc.close();
        destDoc.close();
        File dd = new File("filled-form.pdf");
        dd.delete();
        File ss = new File("tmp.pdf");
        ss.delete();
        pdf.close();
    }

    public static void createNewDoc() throws IOException {
        //doc.copyPagesTo(1,1,existingPdf,2);
        PdfReader reader = new PdfReader(ddd);
        PdfWriter writer = new PdfWriter("tmp.pdf");
        PdfDocument document = new PdfDocument(reader,writer);
        PdfAcroForm cmsForm = PdfAcroForm.getAcroForm(document, true);
        cmsForm.getField("pt_name").setValue("khaled");
        document.removePage(2);
        cmsForm.flattenFields();
        document.close();

    }

}
