package com.cob.billing.usecases.bill.tools.modifier.rule.merger;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModifierMerger {
    public static List<String> end(String[] orgArr, String[] modArr) {
        List<String> org = Arrays.asList(orgArr);
        List<String> mod = Arrays.asList(modArr);
        /* Edge case: if mod is fully occupied (4 elements), return mod */
        if (mod.size() == 4) {
            return mod;
        }

        // Step 1: Prepare to handle duplicates and appending mod at the end
        Set<String> resultSet = new LinkedHashSet<>(org); // Start with org array

        // Step 2: Remove duplicates between org and mod
        for (String modValue : mod) {
            resultSet.remove(modValue); // Ensure mod values are not already in org
        }

        // Step 3: Add mod values at the end of the org array
        resultSet.addAll(mod);

        // Step 4: Ensure the result has exactly 4 elements, by trimming from the start if needed
        return resultSet.stream().skip(Math.max(0, resultSet.size() - 4)).collect(Collectors.toList());
    }

    /*
    i have an array of four elements let call it org and another array with the same length called mod . i need to append mod to the front of the org array with the following rules:
        1- if the mod array is fully occupied like ["GP","59","KX","KL"] , then the result array will be the mod array and this will be an edge scenario
        2- if the mod array has only one element or more but not fully occupied, then may be we have two scenarios:
	        a- the result array don't have mod value let suggest that scenarios:
		        -- org : ["KX","KC","KK","59"] , mod ["GP"] , then the result will be ["GP","KC","KK","59"]
			       description : The GP replace KX because of the is no empty slot in org , so we can't shift
		        -- org  : ["KX","KC","KK"] , mod ["GP"] , then the result will be ["GP","KX","KC","KK"]
			        description : first we will shift right the org by one and then add the mod to the empty slot
		        -- org  : ["KX","KC","KK"] , mod ["GP","59"] , then the result will be ["GP","59","KX","KC"]
			        description : first we will shift right the org by and then append mod the shifted org and remove KK because there is no free slot for it
	        b- the result array  have mod(s) value let suggest that scenarios:
		        -- org : ["KX","KC","KV","GP"] , mod ["GP"] , then the result will be ["GP","KX","KC","KV"]
			        description : first there is no empty slot , so shift will not applied the first result will be ["GP","KC","KV","GP"] , but this is wrong. We have rule the result should not have duplicate value and "GP" is duplicated , and another rule we can't lose any of org mpdifier to keep duplicated value in result . Based on these rules we will remove last "GP" and keep "KX" and shift it like ["GP","KX","KC","KV"]
		        -- Consider mod have multiple values except the edge scenario and apply the previous scenario
     */
    public static List<String> front(String[] orgArr, String[] modArr) {
        List<String> org = Arrays.asList(orgArr);
        List<String> mod = Arrays.asList(modArr);
        /* Edge case: if mod is fully occupied (4 elements), return mod */
        if (mod.size() == 4) {
            return mod;
        }

        // Step 1: Check if mod values are already in org and handle duplicates
        Set<String> resultSet = new LinkedHashSet<>(mod);

        // Step 2: Handle shifting and replacing logic based on mod size
        int shiftSize = mod.size();
        if (shiftSize > 0) {
            // Shift the org array to the right by mod.size()
            for (int i = 0; i < org.size(); i++) {
                if (resultSet.contains(org.get(i))) {
                    // Skip the mod elements already in the org
                    continue;
                } else {
                    resultSet.add(org.get(i));
                }
                if (resultSet.size() == 4) {
                    break; // We only need a result of 4 elements
                }
            }
        }
        return resultSet.stream().limit(4).collect(Collectors.toList());
    }
}
