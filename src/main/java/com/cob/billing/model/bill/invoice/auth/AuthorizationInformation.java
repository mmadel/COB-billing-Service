package com.cob.billing.model.bill.invoice.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AuthorizationInformation {
    /*
        [0] start date
        [1] expiry date
        [2] authorization id
        [3] insurance company id
     */
    private List<Long[]> authorizationsMetaData = new ArrayList<>();
    private Boolean turning;
    private Boolean selected;
}
