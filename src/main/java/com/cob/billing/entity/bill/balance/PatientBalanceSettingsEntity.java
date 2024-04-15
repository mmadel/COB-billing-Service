package com.cob.billing.entity.bill.balance;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "patient_balance_settings")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class PatientBalanceSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "billing_provider", columnDefinition = "json")
    @Type(type = "json")
    private PatientBalanceBillingProviderSettings patientBalanceBillingProviderSettings;

    @Column(name = "balance_account", columnDefinition = "json")
    @Type(type = "json")
    private PatientBalanceAccountSettings patientBalanceAccountSettings;
}
