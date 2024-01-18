package com.cob.billing.model.clinical.insurance.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsuranceCompanyHolder {
    private Long id;
    private String name;
    private InsuranceCompanyVisibility visibility;
}
