package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.Deflater;

@Component
public class UploadCMSFileUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    public void persist(List<String> files, List<Long> records) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
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
        byte[] originalFile = outputStream.toByteArray();
        System.out.println(originalFile.length);
        byte[] compressedFile = compress(originalFile);
        System.out.println(compressedFile.length);
        List<PatientInvoiceEntity> toBeUpdated = new ArrayList<>();
        records.forEach(id -> {
            PatientInvoiceEntity entity = patientInvoiceRepository.findById(id).get();
            entity.setCmsDocument(compressedFile);
            toBeUpdated.add(entity);
        });
        patientInvoiceRepository.saveAll(toBeUpdated);
    }
    private byte[] compress(byte[] input){
        Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION, false);
        compressor.setInput(input);
        compressor.finish();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;
        while (!compressor.finished()) {
            readCount = compressor.deflate(readBuffer);
            if (readCount > 0) {
                bao.write(readBuffer, 0, readCount);
            }
        }
        compressor.end();
        return bao.toByteArray();
    }
}
