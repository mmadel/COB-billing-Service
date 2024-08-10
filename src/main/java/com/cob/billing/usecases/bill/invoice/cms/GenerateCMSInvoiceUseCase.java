package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.InvoiceGenerationResponse;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.CheckModifierRuleUseCase;
import com.cob.billing.usecases.clinical.patient.auth.CheckAuthorizationUseCase;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class GenerateCMSInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;

    @Autowired
    CheckAuthorizationUseCase checkAuthorizationUseCase;

    @Autowired
    CheckModifierRuleUseCase checkModifierRuleUseCase;
    @Autowired
    UploadCMSFileUseCase uploadCMSFileUseCase;
    @Transactional
    public void generate(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException, IllegalAccessException, AuthorizationException {
        checkModifierRuleUseCase.check(invoiceRequest);
        checkAuthorizationUseCase.check(invoiceRequest);
        List<Long> records = createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
        List<String> files = createCMSDocumentUseCase.createCMSDocument(invoiceRequest);
        InvoiceGenerationResponse invoiceGenerationResponse= InvoiceGenerationResponse.builder()
                .files(files)
                .records(records)
                .build();
        printCMSDocument(files , response);
        persistCMSDocument(invoiceGenerationResponse);
    }
    private void printCMSDocument(List<String> files , HttpServletResponse response) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for (String file : files) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
        }
        merger.close();
    }
    private void persistCMSDocument(InvoiceGenerationResponse invoiceGenerationResponse) throws IOException {
        uploadCMSFileUseCase.persist(invoiceGenerationResponse.getFiles(), invoiceGenerationResponse.getRecords());
    }

}
