package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Component
public class DownLoadCMSUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    public ByteArrayResource download(Long invoiceId) throws IOException, DataFormatException {
        PatientInvoiceEntity patientInvoice = patientInvoiceRepository.findBySubmissionId(invoiceId).get();
        ByteArrayResource resource = new ByteArrayResource(decompress(patientInvoice.getCmsDocument(),false));
        return resource;
    }
    public  byte[] decompress(byte[] input, boolean GZIPFormat)
            throws IOException, DataFormatException {
        Inflater decompressor = new Inflater(GZIPFormat);
        decompressor.setInput(input);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;
        while (!decompressor.finished()) {
            readCount = decompressor.inflate(readBuffer);
            if (readCount > 0) {
                bao.write(readBuffer, 0, readCount);
            }
        }
        decompressor.end();
        return bao.toByteArray();
    }
}
