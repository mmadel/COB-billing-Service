package com.cob.billing.usecases.security.kc.util;

import com.cob.billing.model.security.RoleScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeRolesNamesChecker {
    public static List<String> check(List<RoleScope> roles) {
        List<String> compositeRoles = new ArrayList<>();
        for (RoleScope roleScope : roles) {
            if (roleScope.getRole().equals("billing-role"))
                compositeRoles.addAll(Arrays.asList("invoice-billing-role"
                        , "fee-schedule-billing-role"
                        , "modifier-rule-billing-role"));
            if (roleScope.getRole().equals("payment-role"))
                compositeRoles.addAll(Arrays.asList("batch-insurance-payment-role"
                        , "batch-client-payment-role"
                        , "balance-statement-payment-role"));
            if (roleScope.getRole().equals("provider-role"))
                compositeRoles.addAll(Arrays.asList("soild-provider-role"
                        , "referring-provider-role"));
            if (roleScope.getRole().equals("admin-tool-role"))
                compositeRoles.addAll(Arrays.asList("group-info-admin-tool-role"
                        , "insurance-mapping-admin-tool-role"
                        , "session-default-admin-tool-role"
                        , "account-management-admin-tool-role"));
        }
        return compositeRoles;
    }
}
