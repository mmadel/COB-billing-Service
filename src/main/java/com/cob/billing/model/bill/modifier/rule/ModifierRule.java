package com.cob.billing.model.bill.modifier.rule;

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
    List<Rule> rules;
}
