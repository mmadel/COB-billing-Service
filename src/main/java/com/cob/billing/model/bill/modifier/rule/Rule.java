package com.cob.billing.model.bill.modifier.rule;

import com.cob.billing.entity.bill.modifier.rule.ModifierAppender;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Rule {
    private Long id;
    private String modifier;
    private String cptCode;
    private ModifierAppender appender;
    InsuranceCompanyHolder insurance;
}
