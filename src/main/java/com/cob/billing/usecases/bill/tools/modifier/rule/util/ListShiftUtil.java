package com.cob.billing.usecases.bill.tools.modifier.rule.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListShiftUtil {
    public static List<String> leftShift(List<String> firstList, List<String> secondList) {
        List<String> result = new ArrayList<>(4);
        result.addAll(secondList);
        int remainingSize = 4 - secondList.size();
        result.addAll(firstList.subList(0, Math.min(firstList.size(), remainingSize)));
        return result.subList(0, 4); // Ensure the list size is 4
    }

    public static List<String> rightShift(List<String> firstList, List<String> secondList) {
        List<String> result = new ArrayList<>(4);
        int remainingSize = 4 - secondList.size();
        result.addAll(firstList.subList(Math.max(0, firstList.size() - remainingSize), firstList.size()));
        result.addAll(secondList);
        return result.subList(0, 4); // Ensure the list size is 4
    }
}
