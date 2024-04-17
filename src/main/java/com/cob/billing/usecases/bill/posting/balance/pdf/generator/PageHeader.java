package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.cob.billing.entity.bill.balance.PatientBalanceBillingProviderSettings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PageHeader {
    private static double total;

    public static void create(Document document,
                              double totalBalance,
                              PatientBalanceBillingProviderSettings patientBalanceBillingProviderSettings
    ) throws IOException {
        total = totalBalance;
        Table header = new Table(3).setWidth(UnitValue.createPercentValue(100));
        header.addCell(CellCreator.create(leftSection(patientBalanceBillingProviderSettings.getLineOne(),
                patientBalanceBillingProviderSettings.getLineTwo(),
                patientBalanceBillingProviderSettings.getLineThree()), TextAlignment.LEFT));
        header.addCell(CellCreator.create(new Paragraph().setWidth(30), TextAlignment.CENTER));
        header.addCell(CellCreator.create(rightSection(), TextAlignment.RIGHT));
        document.add(header);
    }

    private static Paragraph leftSection(String lineOne, String lineTwo, String lineThree) throws IOException {
        Paragraph paragraph = new Paragraph()
                .setFontSize(9);
        paragraph.add(new Text(lineOne + "\n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
        paragraph.add(new Text(lineTwo + "\n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        paragraph.add(new Text(lineThree).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        return paragraph;
    }

    private static Table rightSection() throws IOException {
        Table table = new Table(3).setWidth(UnitValue.createPercentValue(100));
        table.addCell(new Cell().add(getInvoiceDateParagraph()));
        table.addCell(new Cell().add(getInvoiceNumberParagraph()));
        table.addCell(new Cell().add(getBalanceParagraph()).setBackgroundColor(new DeviceRgb(255, 255, 204)));
        return table;
    }

    private static Paragraph getInvoiceDateParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Invoice date: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text(getInvoiceDate()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private static Paragraph getInvoiceNumberParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Invoice Number: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("S" + getInvoiceNumber()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private static Paragraph getBalanceParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("balance due: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("$" + total).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    public static String getInvoiceNumber() {
        int number = new Random().nextInt(900000) + 100000;
        return String.format("%06d", number);
    }

    public static String getInvoiceDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/YYYY");
        return sdfDate.format(new Date().getTime());
    }

}
