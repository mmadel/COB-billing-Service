package com.cob.billing.model.bill.invoice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoiceRequestCreation {
    private List<Long> serviceCodeIds;
    private Boolean isOneDateServicePerClaim;
    private String delayedReason;
}
