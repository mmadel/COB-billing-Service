package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;

import java.io.IOException;
import java.util.List;

public class ClientTableCreator extends TableCreator {
    float[] columnsWidth = {5, 45, 30, 30, 25};
    String[] columnsName = {"LOC", "Service Facility", "Name", "Case", "ICD"};


    public void build(List<ClientBalanceAccount> data) throws IOException {
        create(columnsWidth, columnsName);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getLoc()));
                table.addCell(createCell(data.get(i).getFacilityAddress(),7));
                table.addCell(createCell(data.get(i).getClientName()));
                table.addCell(createCell(data.get(i).getCaseTitle()));
                table.addCell(createCell(data.get(i).getIcdCodes()));
            }
        }

    }
}
