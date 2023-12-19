package com.cob.billing.model.bill.posting;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostingInsuranceCompany {
    private String displayName;
    private Long insuranceCompanyId;
    private Long payerId;
}
