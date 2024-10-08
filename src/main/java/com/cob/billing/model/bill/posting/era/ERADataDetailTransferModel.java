package com.cob.billing.model.bill.posting.era;

import com.cob.billing.model.integration.claimmd.era.ClaimAdjustmentReasonCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
public class ERADataDetailTransferModel {
    private String payerName;
    private double totalPaidAmount;
    private String check_number;
    private String paymentMethod;
    private Integer totalLines;
    Map<String,List<ERALineTransferModel>> patientLines;
    Map<String, List<ClaimAdjustmentReasonCode>> patientReasonCodes;

}
