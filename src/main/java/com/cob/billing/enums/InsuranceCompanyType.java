package com.cob.billing.enums;

public enum InsuranceCompanyType {
    Medicare("Medicare"),
    Medicaid("Medicare"),
    Automobile_Medical("Automobile_Medical"),
    Workers_Compensation_Health("Workers_Compensation_Health"),
    PPO("PPO"),
    HMO("HMO"),
    EPO("EPO"),
    POS("POS"),
    HDHPS("HDHPS");

    private final String value;

    InsuranceCompanyType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
