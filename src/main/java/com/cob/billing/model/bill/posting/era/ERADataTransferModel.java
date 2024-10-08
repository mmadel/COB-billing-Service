package com.cob.billing.model.bill.posting.era;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class ERADataTransferModel {
    private Integer eraId;
    private String checkNumber;
    private Integer lines;
    private Integer unAppliedLines;
    private String payerName;
    private String receivedDate;
    private double paidAmount;
    private String paidDate;
    private Boolean seen;
    private String checkType;
    private ERADataDetailTransferModel eraDetails;
}
