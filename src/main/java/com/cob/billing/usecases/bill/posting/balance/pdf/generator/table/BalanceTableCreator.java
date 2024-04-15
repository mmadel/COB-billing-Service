package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.cob.billing.util.ListUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class BalanceTableCreator extends TableCreator {
    float[] columnsWidth = {10f, 5f, 5f, 10f, 5f, 15f, 5f, 5f, 5f, 5f, 5f};
    String[] columnsName = {"DOS", "LOC", "POS", "Service", "Units", "Provider", "Charge", "Adj", "Ins", "Patient", "Balance"};

    public BalanceTableCreator(String[] removableItems) {
        boolean isProviderDisable = Arrays.stream(removableItems).anyMatch("PRO"::equals);
        if (isProviderDisable){
            columnsWidth = ListUtils.remove(columnsWidth, 5);
            columnsName = ListUtils.remove(columnsName, 5);
        }
        boolean isLOCDisable = Arrays.stream(removableItems).anyMatch("LOC"::equals);
        if (isLOCDisable){
            columnsWidth = ListUtils.remove(columnsWidth, 1);
            columnsName = ListUtils.remove(columnsName, 1);
        }
        boolean isPOSDisable = Arrays.stream(removableItems).anyMatch("POS"::equals);
        if (isPOSDisable){
            columnsWidth = ListUtils.remove(columnsWidth, 2);
            columnsName = ListUtils.remove(columnsName, 2 );
        }
    }

    public void build(List<ClientBalancePayment> data) throws IOException {
        create(columnsWidth, columnsName);
        if (data != null)
            for (int i = 0; i < data.size(); i++) {
                DateFormat format = new SimpleDateFormat(
                        "MMM dd yyyy");
                table.addCell(createCell(format.format(data.get(i).getDos())));
                table.addCell(createCell(data.get(i).getLoc()));
                table.addCell(createCell(data.get(i).getPlaceOfCode().split("_")[1]));
                table.addCell(createCell(data.get(i).getServiceCode()));
                table.addCell(createCell(data.get(i).getUnits().toString()));
                //table.addCell(createCell(data.get(i).getProvider()));
                table.addCell(createCell(data.get(i).getCharge().toString()));
                table.addCell(createCell(data.get(i).getAdjustPayment().toString()));
                table.addCell(createCell(data.get(i).getInsCompanyPayment().toString()));
                table.addCell(createCell(data.get(i).getClientPayment().toString()));
                table.addCell(createCell(data.get(i).getBalance().toString()));
            }
    }
}
