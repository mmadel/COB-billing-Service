package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public abstract class TableCreator {
    public Table table;

    public void create(float[] columnWidths, String[] columnNames) throws IOException {
        table = new Table(UnitValue.createPercentArray(columnWidths));
        for (int i = 0; i < columnWidths.length; i++) {
            Cell header = new Cell().add(new Paragraph(columnNames[i])
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                            .setFontSize(9)
                            .setFontColor(ColorConstants.WHITE))
                    .setStrokeWidth(30)
                    .setBackgroundColor(new DeviceRgb(0, 0, 153))
                    .setPadding(0);
            table.addHeaderCell(header);
        }
    }

    Cell createCell(String data) throws IOException {
        return createCell(data, 9);
    }
    Cell createCell(String data , int fontSize) throws IOException {
        Cell cell = new Cell().add(new Paragraph(data)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                        .setFontSize(fontSize)
                        .setFontColor(ColorConstants.BLACK))
                .setStrokeWidth(30)
                .setPadding(0);
        return cell;
    }
}
