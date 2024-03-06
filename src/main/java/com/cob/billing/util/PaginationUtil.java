package com.cob.billing.util;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationUtil {
    public static <T> List<T> paginate(List<T> list, int pageNumber, int pageSize) {
        // Calculate start and end index for the sublist
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        // Use Java 8 Streams to slice the list
        return list.stream()
                .skip(startIndex)
                .limit(endIndex - startIndex)
                .collect(Collectors.toList());
    }
}
