package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.enums.LocationTableStructure;

import java.io.IOException;
import java.util.List;

public class LocationTableCreator extends TableCreator {
    LocationTableStructure locationTableStructure;
    public LocationTableCreator(LocationTableStructure locationTableStructure){
        this.locationTableStructure= locationTableStructure;
    }

    public void build(List<ClientBalanceAccount> data) throws IOException {
        switch (locationTableStructure){
            case Full:
                fillFullTable(data);
                break;
            case ICDCodes:
                fillWithoutICDCodes(data);
                break;
        }
    }
    private void fillFullTable(List<ClientBalanceAccount> data) throws IOException {
        float[] columnsWidth = {5, 45, 30, 30, 25};
        String[] columnsName = {"LOC", "Service Facility", "Name", "Case", "ICD"};
        create(columnsWidth, columnsName);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getLoc()));
                table.addCell(createCell(data.get(i).getFacilityAddress(),8));
                table.addCell(createCell(data.get(i).getClientName()));
                table.addCell(createCell(data.get(i).getCaseTitle()));
                table.addCell(createCell(data.get(i).getIcdCodes()));
            }
        }
    }
    private void fillWithoutICDCodes(List<ClientBalanceAccount> data) throws IOException {
        float[] columnsWidth = {10, 70, 10, 10};
        String[] columnsName = {"LOC", "Service Facility", "Name", "Case"};
        create(columnsWidth, columnsName);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getLoc()));
                table.addCell(createCell(data.get(i).getFacilityAddress(),9));
                table.addCell(createCell(data.get(i).getClientName()));
                table.addCell(createCell(data.get(i).getCaseTitle()));
            }
        }
    }
}
