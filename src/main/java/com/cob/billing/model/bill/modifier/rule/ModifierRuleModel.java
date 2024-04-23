package com.cob.billing.model.bill.modifier.rule;

import com.cob.billing.entity.bill.modifier.rule.ModifierAppender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModifierRuleModel {
    private Long id;

    private String modifier;

    private String cptCode;

    private ModifierAppender appender;
}
