package com.cob.billing.usecases.bill.posting.batching.pdf;

import com.cob.billing.model.bill.posting.paymnet.batch.TotalPaymentModel;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.TableCreator;
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

public class TotalPaymentCreator {
    public Table table;
    protected float[] columnWidths;
    protected String[] columnNames;

    public TotalPaymentCreator() throws IOException {
        columnWidths = new float[]{10, 10, 70, 10};
        columnNames = new String[]{"Date", "Method", "Description", "Payment"};
        createTableStructure();
    }

    protected void fillTableData() throws IOException {

    }

    private void createTableStructure() throws IOException {
        table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
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
