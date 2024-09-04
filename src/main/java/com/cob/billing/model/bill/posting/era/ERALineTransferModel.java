package com.cob.billing.model.bill.posting.era;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ERALineTransferModel {
    private double BillAmount;
    private double allowedAmount;
    // AdjustAmount = B1illAmount - (paidAmount - (deductAmount + coInsuranceAmount + coPaymentAmount))
    private double AdjustAmount;
    private double deductAmount;
    private double coInsuranceAmount;
    private double coPaymentAmount;
    private double paidAmount;
    private String[] reasons;
    private Long cptCode;
    private Integer units;
    private String dos;
    private String claimId;


}
