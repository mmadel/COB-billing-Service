package com.cob.billing.model.bill.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthorizationSession {
    private Long id;
    private Long startDate;
    private Long expiryDate;
    private Integer remainingValue;
    private Long insuranceCompanyId;
    private String authorizationNumber;
}
