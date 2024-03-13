package com.cob.billing.model.bill.invoice.tmp.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AuthorizationSelection {
    /*
        It will be like :
            [0] start date
            [1] expiry date
            [2] authorization id
            [3] session id
     */
    List<Long[]> authorizations;

    private Integer remainingCounter;
    private Long expiryDate;
    private String authorizationNumber;
}
