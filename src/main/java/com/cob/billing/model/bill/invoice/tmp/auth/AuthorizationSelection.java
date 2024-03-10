package com.cob.billing.model.bill.invoice.tmp.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizationSelection {
    private Long startDate;
    private Long expiryDate;
    private Integer remainingCounter;
    private String authorizationNumber;
}
