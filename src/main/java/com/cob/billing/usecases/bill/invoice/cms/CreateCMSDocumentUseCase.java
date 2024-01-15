package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
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

@Component
public class CreateCMSDocumentUseCase {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    FillCMSDocumentUseCase fillCMSDocumentUseCase;
    PdfReader cmsTemplate;
    PdfAcroForm cmsForm;
    PdfDocument cmsFile;



    public void create(InvoiceRequest invoiceRequest
            , HttpServletResponse response) throws IOException {
        readCMSTemplate();
        createCMSFile(response);
        fillCMSDocument(invoiceRequest);
        cmsFile.addPage(cmsFile.getFirstPage());
        closeCMSDocument();
    }


    private void readCMSTemplate() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:form-cms1500.pdf");
        cmsTemplate = new PdfReader(resource.getFilename());
    }

    private void createCMSFile(HttpServletResponse response) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        cmsFile = new PdfDocument(cmsTemplate, pdfWriter);
        cmsFile.removePage(2);
        cmsForm = PdfAcroForm.getAcroForm(cmsFile, true);
    }

    private void fillCMSDocument(InvoiceRequest invoiceRequest) {
        fillCMSDocumentUseCase.fill(invoiceRequest,cmsForm);
        customizeTemplate();
    }

    private void customizeTemplate() {
        cmsForm.removeField("Clear Form");
        cmsForm.flattenFields();
    }

    private void closeCMSDocument() throws IOException {
        cmsFile.close();
        cmsTemplate.close();
    }

}
