package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.cob.billing.usecases.bill.invoice.cms.creator.multiple.CreateMultipleCMSClaimsUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.single.CreateCMSClaimUseCase;
import com.cob.billing.util.BeanFactory;
import com.cob.billing.util.FileCompressor;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateCMSFileUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    private CMSClaimCreator cmsClaimCreator;
    private Boolean[] flags;
    PdfMerger merger;

    public void create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        pickClaimCreator(invoiceRequest);
        createClaim(invoiceRequest);
    }

    public void pickClaimCreator(InvoiceRequest invoiceRequest) {
        Boolean hasMultipleItems = MultipleItemsChecker.check(invoiceRequest);
        flags = MultipleItemsChecker.getMultipleFlags();
        if (hasMultipleItems) {
            cmsClaimCreator = BeanFactory.getBean(CreateMultipleCMSClaimsUseCase.class);
        } else {
            cmsClaimCreator = BeanFactory.getBean(CreateCMSClaimUseCase.class);
        }
    }

    public void createClaim(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        invoiceRequest.setFileNamesServiceLinesMapper(cmsClaimCreator.create(invoiceRequest, flags));
    }

    public void generateCMSFile(InvoiceRequest invoiceRequest) throws IOException {
        List<String> fileNames = invoiceRequest.getFileNamesServiceLinesMapper().keySet().stream().collect(Collectors.toList());
        for (String fileName : fileNames) {
            createFile(fileName);
        }
        merger.close();
    }

    public void upload(InvoiceRequest invoiceRequest) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        PdfMerger merger = new PdfMerger(pdf);
        List<String> fileNames = invoiceRequest.getFileNamesServiceLinesMapper().keySet().stream().collect(Collectors.toList());
        for (String file : fileNames) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
            tmpFile.delete();
        }
        merger.close();
        byte[] originalFile = outputStream.toByteArray();
        byte[] compressedFile = FileCompressor.compress(originalFile);
        List<PatientInvoiceEntity> toBeUpdated = new ArrayList<>();
        invoiceRequest.getRecords().forEach(id -> {
            PatientInvoiceEntity entity = patientInvoiceRepository.findById(id).get();
            entity.setCmsDocument(compressedFile);
            toBeUpdated.add(entity);
        });
        patientInvoiceRepository.saveAll(toBeUpdated);
    }

    public void createPdfMerger(OutputStream os) {
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdf = new PdfDocument(writer);
        merger = new PdfMerger(pdf);
    }

    private File createFile(String fileName) throws IOException {
        File tmpFile = new File(fileName);
        PdfReader source = new PdfReader(tmpFile);
        PdfDocument sourceDoc = new PdfDocument(source);
        merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
        sourceDoc.close();
        return tmpFile;
    }
}
