package com.cob.billing.model.bill.fee.schedule;

import com.cob.billing.enums.RateType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeeScheduleLineModel {
    private Long id;
    private String cptCode;
    private RateType rateType;
    private Integer perUnit;
    private Float chargeAmount;

}
