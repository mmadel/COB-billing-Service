package com.cob.billing.model.bill.invoice;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequestCreation {
    private Long serviceCodeId;
    private Boolean isOneDateServicePerClaim;
    private String delayedReason;
}
