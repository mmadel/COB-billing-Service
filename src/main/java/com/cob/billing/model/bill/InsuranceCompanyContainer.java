package com.cob.billing.model.bill;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompanyContainer {

    private String payerName;
    private String payerDisplayName;
    private Long payerId;
    private String insuranceCompanyName;
    private Long insuranceCompanyId;
}
