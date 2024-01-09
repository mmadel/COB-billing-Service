package com.cob.billing.entity.bill.insurance.compnay;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.common.BasicAddress;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "insurance_company")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class InsuranceCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "addresses", columnDefinition = "json")
    @Type(type = "json")
    private BasicAddress address;

    @Column(name = "payer", columnDefinition = "json")
    @Type(type = "json")
    private Payer payer;


}
