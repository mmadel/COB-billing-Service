package com.cob.billing.model.bill.posting.paymnet.batch.pdf;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientBatchReceiptDetailsPaymentInfo {
    private Long dos;
    private String sessionCase;
    private String location;
    private Float pmtAmount;
}
