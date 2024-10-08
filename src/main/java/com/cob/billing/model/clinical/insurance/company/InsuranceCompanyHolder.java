package com.cob.billing.model.clinical.insurance.company;

import com.cob.billing.model.common.BasicAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceCompanyHolder {
    private Long id;
    private String name;
    private BasicAddress address;
    private InsuranceCompanyVisibility visibility;
}
