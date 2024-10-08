package com.cob.billing.util;

import com.cob.billing.model.bill.posting.era.ERADataTransferModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ERAListSorterByDate {
    public void sortByReceivedDateDesc(List<ERADataTransferModel> list) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        Collections.sort(list, (o1, o2) -> {
            try {
                Date date1 = dateFormat.parse(o1.getReceivedDate());
                Date date2 = dateFormat.parse(o2.getReceivedDate());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format", e);
            }
        });
    }
}
