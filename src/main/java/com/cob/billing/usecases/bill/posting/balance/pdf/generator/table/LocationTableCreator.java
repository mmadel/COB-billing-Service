package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;

import java.io.IOException;
import java.util.List;

public class LocationTableCreator extends TableCreator<ClientBalanceAccount> {
    boolean[] settings;

    public LocationTableCreator(List<ClientBalanceAccount> data, boolean[] settings) {
        super(ColumnRuleEngine.returnColumnsWidth(settings), ColumnRuleEngine.returnColumnsName(settings));
        this.data = data;
        this.settings = settings;
    }

    @Override
    protected void fillTableData() throws IOException {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getLoc()));
                table.addCell(createCell(data.get(i).getFacilityAddress(), 8));
                table.addCell(createCell(data.get(i).getClientName()));
                table.addCell(createCell(data.get(i).getCaseTitle()));
                if (settings[0])
                    table.addCell(createCell(data.get(i).getIcdCodes()));
            }
        }
    }
}
