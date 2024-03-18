package com.cob.billing.model.bill.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PickedAuthorizationSession {
    private Long startDate;
    private Long expiryDate;
    private Integer remainingValue;
    private Long insuranceCompanyId;
    private String authorizationNumber;
}
