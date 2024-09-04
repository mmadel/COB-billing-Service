package com.cob.billing.model.bill.posting.era;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ERADataDetailTransferModel {
    private double totalPaidAmount;
    private String paymentMethod;
    List<ERALineTransferModel> lines;

}
