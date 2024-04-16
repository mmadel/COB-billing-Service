package com.cob.billing.usecases.bill.posting.balance.pdf.generator.table;

import java.util.*;

import static com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.BalanceTableColumnsNames.*;
import static com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ClientBalanceSettingsConstants.*;

public class ColumnRuleEngine {
    private static Map<List<Boolean>, float[]> column_width_rules = new HashMap<>();
    private static Map<List<Boolean>, String[]> column_name_rules = new HashMap<>();

    static {


        column_width_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, DisableLOC, DisablePOS), new float[]{20f, 25f, 5f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, DisableLOC, EnablePOS), new float[]{20f, 5f, 20f, 5f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, EnableLOC, DisablePOS), new float[]{20f, 5f, 20f, 5f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, EnableLOC, EnablePOS), new float[]{10f, 10f, 5f, 20f, 5f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, DisableLOC, DisablePOS), new float[]{10f, 20f, 15f, 15f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, DisableLOC, EnablePOS), new float[]{10f, 5f, 15f, 5f, 15f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, EnableLOC, DisablePOS), new float[]{10f, 5f, 15f, 5f, 15f, 5f, 5f, 5f, 5f, 5f});
        column_width_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, EnableLOC, EnablePOS), new float[]{10f, 5f, 5f, 10f, 5f, 15f, 5f, 5f, 5f, 5f, 5f});


        column_name_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, DisableLOC, DisablePOS), new String[]{DateOfBirth, Service, Units, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, DisableLOC, EnablePOS), new String[]{DateOfBirth, PlaceOfService, Service, Units, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, EnableLOC, DisablePOS), new String[]{DateOfBirth, Location, Service, Units, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableRenderingProvider, EnableLOC, EnablePOS), new String[]{DateOfBirth, Location, PlaceOfService, Service, Units, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, DisableLOC, DisablePOS), new String[]{DateOfBirth, Service, Units, RenderingProvider, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, DisableLOC, EnablePOS), new String[]{DateOfBirth, PlaceOfService, Service, Units, RenderingProvider, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, EnableLOC, DisablePOS), new String[]{DateOfBirth, Location, Service, Units, RenderingProvider, Charge, Adj, Ins, Client, Balance});
        column_name_rules.put(ColumnRuleKeysCreator.key(EnableRenderingProvider, EnableLOC, EnablePOS), new String[]{DateOfBirth, Location, PlaceOfService, Service, Units, RenderingProvider, Charge, Adj, Ins, Client, Balance});

        column_width_rules.put(ColumnRuleKeysCreator.key(EnableICDCodes), new float[]{5, 45, 30, 30, 25});
        column_width_rules.put(ColumnRuleKeysCreator.key(DisableICDCodes), new float[]{10, 70, 10, 10});

        column_name_rules.put(ColumnRuleKeysCreator.key(EnableICDCodes), new String[]{"LOC", "Service Facility", "Name", "Case", "ICD"});
        column_name_rules.put(ColumnRuleKeysCreator.key(DisableICDCodes), new String[]{"LOC", "Service Facility", "Name", "Case"});
    }

    public static float[] returnColumnsWidth(boolean[] configuration) {

        return column_width_rules.get(ColumnRuleKeysCreator.key(configuration));
    }

    public static String[] returnColumnsName(boolean[] configuration) {
        return column_name_rules.get(ColumnRuleKeysCreator.key(configuration));
    }
}
