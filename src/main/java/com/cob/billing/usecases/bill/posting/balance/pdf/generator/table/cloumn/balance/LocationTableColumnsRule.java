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
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableLOC, EnableICDCodes), new float[]{20, 55, 30, 30});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableLOC, DisableICDCodes), new float[]{100});

        column_width_rules.put(ColumnRuleKeysCreator.key(DisableLOC, EnableICDCodes), new float[]{70, 50});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableLOC, DisableICDCodes), new float[]{25, 45, 20});


        column_name_rules.put(ColumnRuleKeysCreator.key(EnableLOC, EnableICDCodes), new String[]{"LOC", "Service Facility", "Case", "ICD"});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableLOC, DisableICDCodes), new String[]{ "Case"});

        column_name_rules.put(ColumnRuleKeysCreator.key(DisableLOC, EnableICDCodes), new String[]{"Case", "ICD"});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableLOC, DisableICDCodes), new String[]{"LOC", "Service Facility", "Case"});
    }

    public static float[] returnColumnsWidth(boolean[] configuration) {

        return column_width_rules.get(ColumnRuleKeysCreator.key(configuration));
    }

    public static String[] returnColumnsName(boolean[] configuration) {
        return column_name_rules.get(ColumnRuleKeysCreator.key(configuration));
    }
}
