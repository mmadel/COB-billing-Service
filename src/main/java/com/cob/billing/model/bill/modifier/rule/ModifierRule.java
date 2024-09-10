package com.cob.billing.model.bill.modifier.rule;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ModifierRule {
    private Long id;
    private String name;
    private Boolean defaultRule;
    private Boolean active;
    private InsuranceCompanyHolder insuranceCompany;
    List<Rule> rules;

}
