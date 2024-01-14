package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceBillingProviderInformation {
    private String businessName;
    private BasicAddress address;
    private String phone;
    private String ssn;
    private String taxId;
    private Boolean identifierFlag;
}
