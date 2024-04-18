package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.cloumn.balance;

import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ColumnRuleKeysCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ClientBalanceSettingsConstants.*;

public class LocationTableColumnsRule {
    private static Map<List<Boolean>, float[]> column_width_rules = new HashMap<>();
    private static Map<List<Boolean>, String[]> column_name_rules = new HashMap<>();

    static {
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableLOC, EnableICDCodes), new float[]{5, 45, 30, 30, 25});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableLOC, DisableICDCodes), new float[]{50, 50});

        column_width_rules.put(ColumnRuleKeysCreator.key(DisableLOC, EnableICDCodes), new float[]{20, 60, 40});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableLOC, DisableICDCodes), new float[]{20, 40, 10, 20});


        column_name_rules.put(ColumnRuleKeysCreator.key(EnableLOC, EnableICDCodes), new String[]{"LOC", "Service Facility", "Name", "Case", "ICD"});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableLOC, DisableICDCodes), new String[]{"Name", "Case"});

        column_name_rules.put(ColumnRuleKeysCreator.key(DisableLOC, EnableICDCodes), new String[]{"Name", "Case", "ICD"});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableLOC, DisableICDCodes), new String[]{"LOC", "Service Facility", "Name", "Case"});
    }

    public static float[] returnColumnsWidth(boolean[] configuration) {

        return column_width_rules.get(ColumnRuleKeysCreator.key(configuration));
    }

    public static String[] returnColumnsName(boolean[] configuration) {
        return column_name_rules.get(ColumnRuleKeysCreator.key(configuration));
    }
}
