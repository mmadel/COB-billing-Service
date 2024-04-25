package com.cob.billing.model.bill.posting.paymnet.batch.pdf;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientBatchReceiptPaymentInfo {
    private Long receivedDate;
    private String paymentMethod;
    private Float totalPayment;
}
