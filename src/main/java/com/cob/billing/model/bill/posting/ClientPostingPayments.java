package com.cob.billing.model.bill.posting;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientPostingPayments {
    private Long clientId;
    private List<BatchServiceLinePayment> batchServiceLinePayments;
}
