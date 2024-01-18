package com.cob.billing.entity.clinical.insurance.compnay;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.common.Address;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "insurance_company_payer")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class InsuranceCompanyPayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "internal_insurance_company_id", referencedColumnName = "id")
    InsuranceCompanyEntity internalInsuranceCompany;

    @Column(name = "payer_id")
    private Long payerId;
    @Column(name = "payer_data", columnDefinition = "json")
    @Type(type = "json")
    private Payer payer;

}
