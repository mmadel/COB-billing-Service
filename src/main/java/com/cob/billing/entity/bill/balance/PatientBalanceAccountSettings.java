package com.cob.billing.entity.bill.balance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientBalanceAccountSettings {
    private boolean taxID;
    private boolean npi;
    private boolean renderingProvider;
    private boolean icdCodes;
    private boolean patientDOB;
    private boolean location;
    private boolean poc;
}
