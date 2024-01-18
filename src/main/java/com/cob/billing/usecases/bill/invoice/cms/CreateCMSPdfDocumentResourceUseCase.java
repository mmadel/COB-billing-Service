package com.cob.billing.usecases.bill.invoice.cms;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateCMSPdfDocumentResourceUseCase {
    @Autowired
    private ResourceLoader resourceLoader;
    PdfReader pdfReader;
    PdfDocument document;
    PdfAcroForm form;

    public void createResource(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:form-cms1500.pdf");
        pdfReader = new PdfReader(resource.getFilename());
        PdfWriter pdfWriter = new PdfWriter(fileName);
        document = new PdfDocument(pdfReader, pdfWriter);
        form = PdfAcroForm.getAcroForm(document, true);
        modifyDocument();

    }

    public PdfAcroForm getForm() {
        return form;
    }

    public void closeResource() throws IOException {
        document.close();
        pdfReader.close();
    }

    private void modifyDocument() {
        document.removePage(2);
        form.removeField("Clear Form");
    }

    public void lockForm() {
        form.flattenFields();
    }
}
