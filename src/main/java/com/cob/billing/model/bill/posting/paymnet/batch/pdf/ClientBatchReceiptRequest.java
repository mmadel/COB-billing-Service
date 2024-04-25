package com.cob.billing.model.bill.posting.paymnet.batch.pdf;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientBatchReceiptRequest {
    private ClientBatchReceiptPatientInfo clientBatchReceiptPatientInfo;
    private ClientBatchReceiptPaymentInfo clientBatchReceiptPaymentInfo;
    private List<ClientBatchReceiptDetailsPaymentInfo> paymentDetails;

}
