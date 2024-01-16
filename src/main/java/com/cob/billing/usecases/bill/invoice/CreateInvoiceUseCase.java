package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceInternalCompanyRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSDocumentUseCase;
import com.cob.billing.usecases.bill.invoice.cms.TestUseCase;
import com.cob.billing.usecases.bill.invoice.record.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.record.MapInvoiceRecordUseCase;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class CreateInvoiceUseCase {
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;
    @Autowired
    CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    MapInvoiceRecordUseCase mapInvoiceRecordUseCase;
    @Autowired
    TestUseCase testUseCase;
    @Autowired
    ResourceLoader resourceLoader;

    @Transactional
    public void create(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException {
        createInvoiceRecordUseCase.createRecord(invoiceRequest.getSelectedSessionServiceLine()
                , invoiceRequest.getInvoiceRequestConfiguration(), invoiceRequest.getPatientInformation().getId());

        mapInvoiceRecordUseCase.mapRecord(invoiceRequest.getInvoiceInsuranceCompanyInformation().getVisibility()
                , invoiceRequest.getInvoiceInsuranceCompanyInformation().getName()
                , createInvoiceRecordUseCase.patientInvoiceRecords);
        List<String> files = testUseCase.test(invoiceRequest, createInvoiceRecordUseCase.patientInvoiceRecords);
        changeSessionStatus(invoiceRequest.getSelectedSessionServiceLine());
        writeCMSDocumentToHttpResponse(response, files);
    }

    private void changeSessionStatus(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        selectedSessionServiceLines.stream()
                .forEach(serviceLine -> {
                    changeSessionStatusUseCase.change(serviceLine.getServiceLine());
                });
    }

    private void writeCMSDocumentToHttpResponse(HttpServletResponse response, List<String> files) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for(int i = 0 ; i < files.size() ; i ++){
            File dd = new File(files.get(i));
            PdfReader source = new PdfReader(dd);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
        }
        merger.close();
    }
}
