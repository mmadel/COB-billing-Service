package com.cob.billing.model.clinical.insurance.company;

import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompanyHolder {
    private Long id;
    private String name;
    private BasicAddress address;
    private InsuranceCompanyVisibility visibility;
}
