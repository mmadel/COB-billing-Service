package com.cob.billing.usecases.security.kc.util;

import com.cob.billing.model.security.RoleScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParentRolesNamesChecker {
    public static List<String> check(List<RoleScope> roles) {
        List<String> parentRoles = new ArrayList<>();
        boolean hasBillingChild = roles.stream().map(roleScope -> roleScope.getRole()).anyMatch(Arrays.asList("invoice-billing-role"
                , "fee-schedule-billing-role"
                , "modifier-rule-billing-role")::contains);
        if (hasBillingChild)
            parentRoles.add("billing-role");


        boolean hasProviderChild = roles.stream().map(roleScope -> roleScope.getRole()).anyMatch(Arrays.asList("invoice-billing-role"
                , "soild-provider-role"
                , "referring-provider-role")::contains);
        if (hasProviderChild)
            parentRoles.add("provider-role");

        boolean hasPaymentChild = roles.stream().map(roleScope -> roleScope.getRole()).anyMatch(Arrays.asList("batch-insurance-payment-role"
                , "batch-client-payment-role"
                , "balance-statement-payment-role")::contains);
        if (hasPaymentChild)
            parentRoles.add("payment-role");

        boolean hasAdminToolsChild = roles.stream().map(roleScope -> roleScope.getRole()).anyMatch(Arrays.asList("batch-insurance-payment-role"
                , "group-info-admin-tool-role"
                , "insurance-mapping-admin-tool-role"
                , "session-default-admin-tool-role"
                , "account-management-admin-tool-role")::contains);
        if (hasAdminToolsChild)
            parentRoles.add("admin-tool-role");
        return parentRoles;
    }
}
