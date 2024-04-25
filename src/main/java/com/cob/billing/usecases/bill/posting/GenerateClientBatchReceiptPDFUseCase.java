package com.cob.billing.usecases.bill.posting;


import com.cob.billing.usecases.bill.posting.balance.pdf.generator.CellCreator;
import com.cob.billing.usecases.bill.posting.batching.pdf.LocationInformationTableCreator;
import com.cob.billing.usecases.bill.posting.batching.pdf.PaymentDetailsTableCreator;
import com.cob.billing.usecases.bill.posting.batching.pdf.TotalPaymentTableCreator;
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
import com.itextpdf.layout.element.Text;
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

        //Create Payment Receipt Header
        Table receiptPaymentTableHeader = new Table(3)
                .setWidth(UnitValue.createPercentValue(100))
                .setBorder(new SolidBorder(1));

        Paragraph paragraph = new Paragraph("Payment Receipt")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13);
        receiptPaymentTableHeader.addCell(CellCreator.create(paragraph, TextAlignment.CENTER));
        document.add(receiptPaymentTableHeader);

        document.add(new Paragraph("\n"));

        //Create Total Payment Received
        Paragraph totalPaymentParagraph = new Paragraph("Total Payment Received")
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13);
        document.add(totalPaymentParagraph);

        TotalPaymentTableCreator totalPaymentTableCreator = new TotalPaymentTableCreator();
        document.add(totalPaymentTableCreator.table);

        document.add(new Paragraph("\n"));

        //Create Payment Details
        Paragraph paymentDetailsParagraph = new Paragraph();
        paymentDetailsParagraph.add(new Text("Payment Details \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(13));
        paymentDetailsParagraph.add(new Text("The total payment received was applied to the following charges.").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9));
        document.add(paymentDetailsParagraph);

        PaymentDetailsTableCreator paymentDetailsTableCreator = new PaymentDetailsTableCreator();
        document.add(paymentDetailsTableCreator.table);

        document.add(new Paragraph("\n"));

        //Create Location
        Paragraph locationParagraph = new Paragraph();
        locationParagraph.add(new Text("Location Information").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(13));
        document.add(locationParagraph);

        LocationInformationTableCreator locationInformationTableCreator  = new LocationInformationTableCreator();
        document.add(locationInformationTableCreator.table);

        document.close();
        return outputStream.toByteArray();
    }
}
