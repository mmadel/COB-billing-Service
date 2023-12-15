package com.cob.billing.model.bill.posting;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompanySearchResult {
    private Long payerId;
    private String payerName;
    private String displayName;
}
