package com.cob.billing.model.bill.insurance.company;

import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompany {
    private Long id;
    private String name;
    private BasicAddress address;
    private Long payerId;
}