package com.cob.billing.configuration;


import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static File ddd = new File("C:\\cob\\documents\\billing\\form-cms15000.pdf");

    public static void main(String[] args) {
        String[] org = {"GP", "80", "39", "90"}; // Original array
        String[] mod = {"59"};
        applyModFromEnd(org, mod);
        System.out.println(Arrays.toString(org));
    }
    public static void applyModFromStart(String[] org, String[] mod) {
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
    }

    // Method to apply mod by shifting left and inserting mod elements at the end
    public static void applyModFromEnd(String[] org, String[] mod) {
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
    }

    // Helper method to filter out elements in mod that already exist in org
    private static String[] filterMod(String[] org, String[] mod) {
        Set<String> orgSet = new HashSet<>(Arrays.asList(org));
        return Arrays.stream(mod)
                .filter(modElem -> modElem != null && !modElem.isEmpty() && !orgSet.contains(modElem))
                .toArray(String[]::new);
    }
}