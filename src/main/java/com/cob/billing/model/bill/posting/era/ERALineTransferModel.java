package com.cob.billing.model.bill.posting.era;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ERALineTransferModel {
    private double billAmount;
    private double allowedAmount;
    // AdjustAmount = B1illAmount - (paidAmount - (deductAmount + coInsuranceAmount + coPaymentAmount))
    private double adjustAmount;
    private double deductAmount;
    private double coInsuranceAmount;
    private double coPaymentAmount;
    private double paidAmount;
    private String[] reasons;
    private Long cptCode;
    private Integer units;
    private String dos;
    private String claimId;
    private Integer chargeLineId;
    private ServiceLinePaymentAction serviceLinePaymentAction;


}
