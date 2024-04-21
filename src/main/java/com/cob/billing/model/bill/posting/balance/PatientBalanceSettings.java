package com.cob.billing.model.bill.posting.balance;

import com.cob.billing.entity.bill.balance.PatientBalanceAccountSettings;
import com.cob.billing.entity.bill.balance.PatientBalanceBillingProviderSettings;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientBalanceSettings {
    private Long id;
    private PatientBalanceBillingProviderSettings patientBalanceBillingProviderSettings;
    private PatientBalanceAccountSettings patientBalanceAccountSettings;
}
