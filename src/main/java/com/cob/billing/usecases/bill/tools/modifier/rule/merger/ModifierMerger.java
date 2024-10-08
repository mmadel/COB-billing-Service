package com.cob.billing.usecases.bill.tools.modifier.rule.merger;

import java.util.*;
import java.util.stream.Collectors;

public class ModifierMerger {
    public static String[] front(String[] org, String[] mod) {
        // Filter out elements from mod that are already in org
        String[] filteredMod = filterMod(org, mod);

        int modLength = filteredMod.length;

        // Shift the org array to the right by modLength
        for (int i = org.length - 1; i >= modLength; i--) {
            org[i] = org[i - modLength];
        }

        // Insert mod values in front of the org array
        for (int i = 0; i < modLength; i++) {
            org[i] = filteredMod[i];
        }
        return org;
    }

    // Method to apply mod by shifting left and inserting mod elements at the end
    public static String[] end(String[] org, String[] mod) {
        // Filter out elements from mod that are already in org
        String[] filteredMod = filterMod(org, mod);

        int modLength = filteredMod.length;

        // Shift the org array to the left by modLength
        for (int i = 0; i < org.length - modLength; i++) {
            org[i] = org[i + modLength];
        }

        // Insert mod values at the end of the org array
        for (int i = 0; i < modLength; i++) {
            org[org.length - modLength + i] = filteredMod[i];
        }
        return org;
    }

    // Helper method to filter out elements in mod that already exist in org
    private static String[] filterMod(String[] org, String[] mod) {
        Set<String> orgSet = new HashSet<>(Arrays.asList(org));
        return Arrays.stream(mod)
                .filter(modElem -> modElem != null && !modElem.isEmpty() && !orgSet.contains(modElem))
                .toArray(String[]::new);
    }
}
