package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSDocumentUseCase;
import com.cob.billing.usecases.bill.invoice.record.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.record.MapInvoiceRecordUseCase;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class CreateInvoiceUseCase {
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;

    @Autowired
    CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    MapInvoiceRecordUseCase mapInvoiceRecordUseCase;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;
    @Autowired
    ResourceLoader resourceLoader;

    @Transactional
    public void create(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException {
        createInvoiceRecordUseCase.createRecord(invoiceRequest.getSelectedSessionServiceLine()
                , invoiceRequest.getInvoiceRequestConfiguration(), invoiceRequest.getPatientInformation().getId());

        mapInvoiceRecordUseCase.mapRecord(invoiceRequest.getInvoiceInsuranceCompanyInformation().getVisibility()
                , invoiceRequest.getInvoiceInsuranceCompanyInformation().getName()
                , createInvoiceRecordUseCase.patientInvoiceRecords);

        changeSessionStatus(invoiceRequest.getSelectedSessionServiceLine());

        createSMCDocument(invoiceRequest, createInvoiceRecordUseCase.patientInvoiceRecords, response);
    }

    private void changeSessionStatus(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        selectedSessionServiceLines
                .forEach(serviceLine -> changeSessionStatusUseCase.change(serviceLine.getServiceLine()));
    }

    private void createSMCDocument(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords
            , HttpServletResponse response) throws IOException {
        List<String> files = createCMSDocumentUseCase.fill(invoiceRequest, patientInvoiceRecords);
        writeCMSDocumentToHttpResponse(response, files);
    }

    private void writeCMSDocumentToHttpResponse(HttpServletResponse response, List<String> files) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for (String file : files) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
            tmpFile.delete();
        }
        merger.close();
    }
}
