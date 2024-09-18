package com.cob.billing.model.bill.posting.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ERADetailsLine {
    private double billAmount;
    private double allowedAmount;
    private double adjustAmount;
    private double editAdjustAmount;
    private double deductAmount;
    private double coInsuranceAmount;
    private double coPaymentAmount;
    private double paidAmount;
    private double editPaidAmount;
    private List<String> reasons;
    private int cptCode;
    private int units;
    private String dos;
    private String claimId;
    private String serviceLineID;
    private String serviceLinePaymentAction;
    private String action;
    private boolean selected;

}
