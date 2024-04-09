package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

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

public class PageHeader {

    public static void create(Document document) throws IOException {
        Table header = new Table(3).setWidth(UnitValue.createPercentValue(100));
        header.addCell(CellCreator.create(leftSection(), TextAlignment.LEFT));
        header.addCell(CellCreator.create(new Paragraph().setWidth(30), TextAlignment.CENTER));
        header.addCell(CellCreator.create(rightSection(), TextAlignment.RIGHT));
        document.add(header);
    }

    private static Paragraph leftSection() throws IOException {
        Paragraph paragraph = new Paragraph()
                .setFontSize(9);
        paragraph.add(new Text("CITYPT \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
        paragraph.add(new Text("8746 20th ave 1L \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        paragraph.add(new Text("Brooklyn, NY, 11214").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
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
        paragraph.add(new Text("03/27/2024").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private static Paragraph getInvoiceNumberParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Invoice Number: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("S000621").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private static Paragraph getBalanceParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("balance due: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("$20.00").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }
}
