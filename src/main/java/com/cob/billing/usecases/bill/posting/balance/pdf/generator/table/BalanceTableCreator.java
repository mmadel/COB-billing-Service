package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BalanceTableCreator extends TableCreator<ClientBalancePayment> {
    boolean[] settings;
    public BalanceTableCreator(List<ClientBalancePayment> data,boolean[] settings) {
        super(ColumnRuleEngine.returnColumnsWidth(settings),ColumnRuleEngine.returnColumnsName(settings));
        this.data = data;
        this.settings = settings;
    }

    @Override
    protected void fillTableData() throws IOException {
        double totalCharge = 0.0f;
        double totalAdj = 0.0f;
        double totalInsPayment = 0.0f;
        double totalPatientPayment = 0.0f;
        double totalBalance = 0.0f;
        if (data != null)
            for (int i = 0; i < data.size(); i++) {
                DateFormat format = new SimpleDateFormat(
                        "MMM dd yyyy");
                table.addCell(createCell(format.format(data.get(i).getDos())).setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(1)));
                if (settings[1])
                    table.addCell(createCell(data.get(i).getLoc()).setBorder(Border.NO_BORDER));
                if (settings[2])
                    table.addCell(createCell(data.get(i).getPlaceOfCode().split("_")[1]).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getServiceCode()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getUnits().toString()).setBorder(Border.NO_BORDER));
                if (settings[0])
                    table.addCell(createCell(data.get(i).getProvider()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getCharge().toString()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getAdjustPayment().toString()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getInsCompanyPayment().toString()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getClientPayment().toString()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getBalance().toString()).setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(1)));

                totalCharge = totalCharge + data.get(i).getCharge();
                totalAdj = totalAdj + data.get(i).getAdjustPayment();
                totalInsPayment = totalInsPayment + data.get(i).getInsCompanyPayment();
                totalPatientPayment = totalPatientPayment + data.get(i).getClientPayment();
                totalBalance = totalBalance + data.get(i).getBalance();
            }
        int colSpan = 0;
        if (table.getNumberOfColumns() == 11)
            colSpan = 6;
        if (table.getNumberOfColumns() == 10)
            colSpan = 5;
        if (table.getNumberOfColumns() == 9)
            colSpan = 4;
        if (table.getNumberOfColumns() == 8)
            colSpan = 3;
        Cell cell = createCell(colSpan, "Total : ").setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(1))
                .setBorderLeft(new SolidBorder(1))
                .setBorderBottom(new SolidBorder(1));
        table.addCell(cell);

        table.addCell(createCell(Double.toString(totalCharge)).setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1)));
        table.addCell(createCell(Double.toString(totalAdj)).setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1)));
        table.addCell(createCell(Double.toString(totalInsPayment)).setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1)));
        table.addCell(createCell(Double.toString(totalPatientPayment)).setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1)));
        table.addCell(createCell(Double.toString(totalBalance)).setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1)))
                .setBorderRight(new SolidBorder(1));
    }

}
