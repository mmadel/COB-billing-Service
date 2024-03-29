package com.cob.billing.model.bill.payer;

import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Payer {
    private Long id;
    private String name;
    private String displayName;
    private Long payerId;
    private BasicAddress address;
}
