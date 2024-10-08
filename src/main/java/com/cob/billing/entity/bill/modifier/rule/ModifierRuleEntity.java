package com.cob.billing.entity.bill.modifier.rule;

import com.cob.billing.model.bill.modifier.rule.Rule;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modifier_rule")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class ModifierRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "default_rule")
    private Boolean defaultRule;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "rules", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<Rule> rules;

    @Column(name = "insurance_company", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private InsuranceCompanyHolder insuranceCompany;


}
