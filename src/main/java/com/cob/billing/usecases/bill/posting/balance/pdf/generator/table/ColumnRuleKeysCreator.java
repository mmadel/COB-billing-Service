package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;

import java.util.ArrayList;

public class ColumnRuleKeysCreator {
    public static ArrayList<Boolean> key(boolean... values) {
        ArrayList<Boolean> key = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            key.add(values[i]);
        }
        return key;
    }
}
