package com.cob.billing.usecases.bill.posting.batching.pdf;

import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptPaymentInfo;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class TotalPaymentTableCreator {
    public Table table;
    protected float[] columnWidths;
    protected String[] columnNames;
    ClientBatchReceiptPaymentInfo data;

    public TotalPaymentTableCreator(ClientBatchReceiptPaymentInfo clientBatchReceiptPaymentInfo) throws IOException {
        columnWidths = new float[]{10, 10, 70, 10};
        columnNames = new String[]{"Date", "Method", "Description", "Payment"};
        this.data = clientBatchReceiptPaymentInfo;
        createTableStructure();
        fillTableData();
    }

    protected void fillTableData() throws IOException {
        if (data != null) {
            table.addCell(createCell(DateConstructor.constructSingle(data.getReceivedDate()), 9).setBorder(Border.NO_BORDER));
            table.addCell(createCell(data.getPaymentMethod().toUpperCase(), 9).setBorder(Border.NO_BORDER));
            table.addCell(createCell("", 9).setBorder(Border.NO_BORDER));
            table.addCell(createCell("$" + data.getTotalPayment().toString(), 9).setBorder(Border.NO_BORDER));
        }
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
}
