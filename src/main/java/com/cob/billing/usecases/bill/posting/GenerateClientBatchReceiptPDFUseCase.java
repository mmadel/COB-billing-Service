package com.cob.billing.usecases.bill.posting;


import com.cob.billing.usecases.bill.posting.balance.pdf.generator.ClientBalanceWarning;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.LineSeparatorCreator;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class GenerateClientBatchReceiptPDFUseCase {
    public byte[] create() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDoc, pageSize);
        LineSeparatorCreator.create(document);
        ClientBalanceWarning.createWarning(document);
        document.close();
        return outputStream.toByteArray();
    }
}
