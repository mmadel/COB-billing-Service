package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Adjustment {
    private String group;
    private String code;
    private String amount;
}
