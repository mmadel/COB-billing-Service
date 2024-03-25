package com.cob.billing.model.bill.invoice.search;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionHistoryCriteria {
    private String insuranceCompany;
    private String client;
    private String provider;
    private String claimId;
    private Long dosStart;
    private Long dosEnd;
    private Long submitStart;
    private Long submitEnd;
}
