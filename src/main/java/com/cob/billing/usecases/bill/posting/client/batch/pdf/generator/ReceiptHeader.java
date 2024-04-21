package com.cob.billing.usecases.bill.posting.client.batch.pdf.generator;

import com.cob.billing.entity.bill.balance.PatientBalanceBillingProviderSettings;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.CellCreator;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ReceiptHeader {
    private static double total;

    public static void createHeader(Document document) throws IOException {

        Table header = new Table(3).setWidth(UnitValue.createPercentValue(100));
        header.setFixedLayout();
        header.addCell(CellCreator.create(leftSection(), TextAlignment.LEFT));
        header.addCell(CellCreator.create(new Paragraph().setWidth(50), TextAlignment.CENTER));
        header.addCell(CellCreator.create(rightSection(), TextAlignment.LEFT));
        document.add(header);
    }

    private static Paragraph leftSection() throws IOException {
        Paragraph paragraph = new Paragraph()
                .setFontSize(9);
        paragraph.add(new Text("CITYPT" + "\n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
        paragraph.add(new Text("8746 20th ave 1L" + "\n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        paragraph.add(new Text("Brooklyn, NY, 11214").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        return paragraph;
    }

    private static Paragraph rightSection() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Text("Payment Receipt" + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13)
                .setTextAlignment(TextAlignment.LEFT));
        SolidLine line = new SolidLine();
        line.setColor(ColorConstants.BLACK);
        LineSeparator lineSeparator = new LineSeparator(line);
        lineSeparator.setWidth(UnitValue.createPercentValue(100));
        lineSeparator.setMarginTop(0);
        paragraph.add(lineSeparator);
        paragraph.add(new Text("Receipt ID: ")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setTextAlignment(TextAlignment.LEFT));
        paragraph.add(new Text("   5" + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT));


        paragraph.add(new Text("Receipt Date: "))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text(getInvoiceDate() + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        return paragraph;
    }

    public static String getInvoiceDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/YYYY");
        return sdfDate.format(new Date().getTime());
    }

}
