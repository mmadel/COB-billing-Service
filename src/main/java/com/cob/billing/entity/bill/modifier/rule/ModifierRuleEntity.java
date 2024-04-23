package com.cob.billing.entity.bill.modifier.rule;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ModifierRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "cpt_code")
    private String cptCode;

    @Column(name = "appender")
    private ModifierAppender appender;
}
