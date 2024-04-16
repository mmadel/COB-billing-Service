package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BalanceTableCreator extends TableCreator<ClientBalancePayment> {

    public BalanceTableCreator(List<ClientBalancePayment> data) {
        this.data = data;
    }

    @Override
    protected void fillTableData() throws IOException {
        if (data != null)
            for (int i = 0; i < data.size(); i++) {
                DateFormat format = new SimpleDateFormat(
                        "MMM dd yyyy");
                table.addCell(createCell(format.format(data.get(i).getDos())));
                if (settings[1])
                    table.addCell(createCell(data.get(i).getLoc()));
                if (settings[2])
                    table.addCell(createCell(data.get(i).getPlaceOfCode().split("_")[1]));
                table.addCell(createCell(data.get(i).getServiceCode()));
                table.addCell(createCell(data.get(i).getUnits().toString()));
                if (settings[0])
                    table.addCell(createCell(data.get(i).getProvider()));
                table.addCell(createCell(data.get(i).getCharge().toString()));
                table.addCell(createCell(data.get(i).getAdjustPayment().toString()));
                table.addCell(createCell(data.get(i).getInsCompanyPayment().toString()));
                table.addCell(createCell(data.get(i).getClientPayment().toString()));
                table.addCell(createCell(data.get(i).getBalance().toString()));
            }
    }

}
