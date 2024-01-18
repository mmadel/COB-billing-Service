package com.cob.billing.model.bill;

import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class InsuranceCompanyConfiguration {
    private Long id;
    private Boolean box32;
    private String box26;
    private Organization billingProvider;
    private Long insuranceCompanyId;
    private InsuranceCompanyVisibility visibility;
    private Boolean isAssignedToPayer;
}
