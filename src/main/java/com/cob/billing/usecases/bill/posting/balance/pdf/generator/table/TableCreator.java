package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;

import com.cob.billing.model.bill.posting.balance.ClientBalanceModel;
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
import java.util.List;

public abstract class TableCreator<T extends ClientBalanceModel> {
    public Table table;
    protected List<T> data;
    protected float[] columnWidths;
    protected String[] columnNames;

    public TableCreator(float[] columnWidths, String[] columnNames) {
        this.columnWidths = columnWidths;
        this.columnNames = columnNames;
    }

    public void create() throws IOException {
        createTableStructure();
        fillTableData();
    }

    private void createTableStructure() throws IOException {
        table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFixedLayout();
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

    protected abstract void fillTableData() throws IOException;

    Cell createCell(String data, int fontSize) throws IOException {
        Cell cell = new Cell().add(new Paragraph(data)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                        .setFontSize(fontSize)
                        .setFontColor(ColorConstants.BLACK))
                .setStrokeWidth(30)
                .setPadding(0);
        return cell;
    }

    Cell createCell(int colSpan, String data) throws IOException {
        Cell cell = new Cell(1, colSpan).add(new Paragraph(data)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.BLACK))
                .setStrokeWidth(30)
                .setPadding(1);
        return cell;
    }
}
