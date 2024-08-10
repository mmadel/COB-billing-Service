package com.cob.billing.usecases.bill.invoice.provider;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.UploadCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.factory.CMSClaimFactory;
import com.cob.billing.usecases.bill.invoice.cms.creator.initiator.ClaimInitiator;
import com.cob.billing.util.BeanFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrintClaimProvider extends ClaimProvider{
    InvoiceRequest invoiceRequest;
    List<String> files;
    public PrintClaimProvider(InvoiceRequest invoiceRequest) {
        super();
        this.invoiceRequest = invoiceRequest;
    }
    @Override
    public void create() throws IOException, IllegalAccessException {
        ClaimInitiator claimInitiator = CMSClaimFactory.getInstance(invoiceRequest);
        files = claimInitiator.create(invoiceRequest);
    }
    @Override
    public void submit() throws IOException, IllegalAccessException {
        create();
        PdfWriter writer = new PdfWriter(invoiceRequest.getResponse().getOutputStream());
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
        UploadCMSFileUseCase uploadCMSFileUseCase = BeanFactory.getBean(UploadCMSFileUseCase.class);
        uploadCMSFileUseCase.persist(files, invoiceRequest.getRecords());
    }
}
