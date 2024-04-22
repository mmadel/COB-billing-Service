package com.cob.billing.model.bill.fee.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeeScheduleModel {
    private String name;
    private Long id;
    private Boolean defaultFee;
    private List<FeeScheduleLineModel> feeLines;
}
