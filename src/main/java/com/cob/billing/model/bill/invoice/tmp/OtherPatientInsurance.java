package com.cob.billing.model.bill.invoice.tmp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtherPatientInsurance {
    private String insuredName;
    private String policyGroup;
    private String planName;
    private String responsibility;
    private String[] assigner;
    private Long createdAt;
}
