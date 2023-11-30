package com.cob.billing.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<Long> returnDifference(List<Long> source, List<Long> destination) {
        return new ArrayList<>((CollectionUtils.removeAll(source, destination)));
    }
}
