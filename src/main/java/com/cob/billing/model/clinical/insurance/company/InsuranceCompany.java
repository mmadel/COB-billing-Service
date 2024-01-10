package com.cob.billing.model.clinical.insurance.company;

import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompany {
    private Long id;
    private String name;
    private BasicAddress address;
    private InsuranceCompanyConfiguration configuration;

    //internal it will be insurance companies that created from billing or consumed from EMR
    //External consumed from Availity
    private InsuranceCompanyVisibility type;
    // [0] payer_id
    // [1] payer_name
    private String[] assigner;
}
