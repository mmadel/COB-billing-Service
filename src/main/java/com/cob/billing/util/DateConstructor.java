package com.cob.billing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConstructor {
    public static String[] construct(Long dateInMilliseconds ){
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-YY");
        Date now = new Date(dateInMilliseconds);
        String formattedDate = sdfDate.format(now);
        return formattedDate.split("-");
    }
}
