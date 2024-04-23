package com.cob.billing.entity.bill.modifier.rule;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "modifier_rule")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
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

    @Column(name = "insurance", columnDefinition = "json")
    @Type(type = "json")
    private InsuranceCompanyHolder insurance;
}
