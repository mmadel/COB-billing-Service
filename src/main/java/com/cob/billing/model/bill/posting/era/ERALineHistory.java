package com.cob.billing.model.bill.posting.era;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ERALineHistory {
    private String dos;
    private String cpt;
    private Integer units;
    private double billed;
    private double adjust;
    private double editatableAdjust;
    private double deduct;
    private double COIN;
    private double COPAY;
    private double paid;
    private double ediatablePaid;
    private String action;
    private String serviceLineId;
}
