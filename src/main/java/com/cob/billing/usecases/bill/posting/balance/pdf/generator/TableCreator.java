package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class TableCreator {
    public static void create(float[] columnWidths, String[] columnNames, Document document) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
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
        document.add(table);
    }
}
