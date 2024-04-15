package com.cob.billing.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListUtils {
    public static List<Long> returnDifference(List<Long> source, List<Long> destination) {
        return new ArrayList<>((CollectionUtils.removeAll(source, destination)));
    }

    public static float[] remove(float[] array, int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= array.length) {
            throw new IllegalArgumentException("Invalid index to remove");
        }
        float[] newArray = new float[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indexToRemove) {
                newArray[j++] = array[i];
            }
        }
        return newArray;
    }

    public static String[] remove(String[] array, int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= array.length) {
            throw new IllegalArgumentException("Invalid index to remove");
        }
        String[] newArray = new String[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indexToRemove) {
                newArray[j++] = array[i];
            }
        }
        return newArray;
    }
}
