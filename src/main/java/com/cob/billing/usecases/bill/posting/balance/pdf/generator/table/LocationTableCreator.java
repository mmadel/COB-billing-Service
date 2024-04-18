package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;


import com.cob.billing.model.bill.posting.balance.ClientBalanceLocation;

import java.io.IOException;
import java.util.List;

public class LocationTableCreator extends TableCreator<ClientBalanceLocation> {
    boolean[] settings;

    public LocationTableCreator(List<ClientBalanceLocation> data, boolean[] settings) {
        super(ColumnRuleEngine.returnColumnsWidth(settings), ColumnRuleEngine.returnColumnsName(settings));
        this.data = data;
        this.settings = settings;
    }

    @Override
    protected void fillTableData() throws IOException {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                table.addCell(createCell(data.get(i).getId() + "",8));
                table.addCell(createCell(data.get(i).getLocationAddress(), 8));
                table.addCell(createCell(data.get(i).getClientName(),8));

                table.addCell(createCell(buildCasesCell(data.get(i).getCases()), 8));
                if (settings[0])
                    table.addCell(createCell(buildCodesCell(data.get(i).getIcdCodes()), 8));
            }
        }
    }

    private String buildCasesCell(List<String> cases) {
        StringBuilder casesCell = new StringBuilder();
        for (String caseTitle : cases) {
            casesCell.append(caseTitle).append("\n");
        }
        return casesCell.toString();
    }

    private String buildCodesCell(List<String> codes) {
        StringBuilder codesCell = new StringBuilder();
        for (String code : codes) {
            codesCell.append(code).append("\n");
        }
        return codesCell.toString();
    }
}
