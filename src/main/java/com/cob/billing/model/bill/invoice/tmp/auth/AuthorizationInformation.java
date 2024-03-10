package com.cob.billing.model.bill.invoice.tmp.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AuthorizationInformation {
    private List<Long[]> authorizationDates;
    private Long insuranceCompanyId;
    private String authNumber;
}
