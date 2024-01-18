package com.cob.billing.model.bill.invoice.tmp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequestConfiguration {
    private String delayedReason;
    private Boolean isOneDateServicePerClaim;
}
