package com.cob.billing.entity.clinical.insurance.compnay;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import com.cob.billing.model.common.BasicAddress;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "insurance_company_external")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class InsuranceCompanyExternalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "addresses", columnDefinition = "json")
    @Type(type = "json")
    private BasicAddress address;

    @Column(name = "payer_id")
    private Long payerId;

}
