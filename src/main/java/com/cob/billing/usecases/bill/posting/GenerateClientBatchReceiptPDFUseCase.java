package com.cob.billing.usecases.bill.posting;


import com.cob.billing.usecases.bill.posting.balance.pdf.generator.CellCreator;
import com.cob.billing.usecases.bill.posting.client.batch.pdf.generator.ReceiptClientTitle;
import com.cob.billing.usecases.bill.posting.client.batch.pdf.generator.ReceiptHeader;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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
        ReceiptHeader.createHeader(document);
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        ReceiptClientTitle.createTitle(document);

        document.add(new Paragraph("\n"));

        Table receiptPaymentTableHeader = new Table(3)
                .setWidth(UnitValue.createPercentValue(100))
                .setBorder(new SolidBorder(1));

        Paragraph paragraph = new Paragraph("Payment Receipt")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13);
        receiptPaymentTableHeader.addCell(CellCreator.create(paragraph, TextAlignment.CENTER));

        document.add(receiptPaymentTableHeader);
        document.close();
        return outputStream.toByteArray();
    }
}
