package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;

import com.cob.billing.model.bill.posting.balance.ClientBalanceProvider;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;

import java.io.IOException;
import java.util.List;

public class ProviderTableCreator extends TableCreator<ClientBalanceProvider> {
    public ProviderTableCreator(List<ClientBalanceProvider> data) {
        super(new float[]{10, 50, 20, 20}, new String[]{"ID", "ProviderName", "NPI", "License #"});
        this.data = data;
    }

    @Override
    protected void fillTableData() throws IOException {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getId().toString()).setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(1)).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getName()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getNpi()).setBorder(Border.NO_BORDER));
                table.addCell(createCell(data.get(i).getLicenseNumber()).setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(1)));
            }
            table.setBorderBottom(new SolidBorder(1));
        }
    }
}
