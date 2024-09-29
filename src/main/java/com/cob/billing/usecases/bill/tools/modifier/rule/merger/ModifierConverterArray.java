package com.cob.billing.usecases.bill.tools.modifier.rule.merger;

public class ModifierConverterArray {
    public static String[] convertModifierToArray(String modifier) {
        String[] parts = modifier.split("\\.");
        // Create a new array of size 4
        String[] result = new String[4];
        // Copy the split parts to the result array
        System.arraycopy(parts, 0, result, 0, Math.min(parts.length, 4));
        // If the length is less than 4, remaining elements will be null
        return result;
    }
}
