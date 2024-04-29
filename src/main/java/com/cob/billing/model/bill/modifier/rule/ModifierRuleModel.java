package com.cob.billing.model.bill.modifier.rule;

import com.cob.billing.entity.bill.modifier.rule.ModifierAppender;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModifierRuleModel {
    private Long id;

    private String name;
    private Boolean defaultRule;
    private Boolean active;

    private String modifier;

    private String cptCode;

    private ModifierAppender appender;

    InsuranceCompanyHolder insurance;
}
