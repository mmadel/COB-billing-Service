package com.cob.billing.model.bill.invoice.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CorrectClaimInformation {
    private String resubmissionCode;
    private String refNumber;
}
