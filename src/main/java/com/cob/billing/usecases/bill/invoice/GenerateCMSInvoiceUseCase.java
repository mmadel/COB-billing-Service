package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSDocumentUseCase;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class GenerateCMSInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;


    @Transactional
    public List<String> generate(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException {

        createInvoiceRecordUseCase.createRecord(invoiceRequest);

        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());

        return createCMSDocumentUseCase.createCMSDocument(invoiceRequest);
    }

}
