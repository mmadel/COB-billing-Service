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
    private String payerName;
    private String receivedDate;
    private BigDecimal paidAmount;
    private Boolean seen;
    private String checkType;
    private ERADataDetailTransferModel eraDetails;
}
