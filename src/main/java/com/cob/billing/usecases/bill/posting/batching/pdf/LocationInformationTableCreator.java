package com.cob.billing.usecases.bill.posting.batching.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class LocationInformationTableCreator {
    public Table table;
    protected float[] columnWidths;
    protected String[] columnNames;

    public LocationInformationTableCreator() throws IOException {
        columnWidths = new float[]{30,70};
        columnNames = new String[]{"Location Name", "Address"};
        createTableStructure();
    }
    protected void fillTableData() throws IOException {

    }
    private void createTableStructure() throws IOException {
        table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(50));
        table.setFixedLayout();
        table.setBorder(Border.NO_BORDER);
        for (int i = 0; i < columnWidths.length; i++) {
            Cell header = new Cell().add(new Paragraph(columnNames[i])
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                            .setFontSize(9)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER)
                    .setStrokeWidth(30)
                    .setBackgroundColor(new DeviceRgb(232, 228, 228))
                    .setPadding(0);
            table.addHeaderCell(header);
        }
    }
}
