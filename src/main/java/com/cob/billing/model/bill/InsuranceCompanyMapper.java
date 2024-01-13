package com.cob.billing.model.bill;

import com.cob.billing.model.bill.payer.Payer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompanyMapper {
    private Long id;
    private Long insuranceCompanyId;
    private Payer payer;
}
