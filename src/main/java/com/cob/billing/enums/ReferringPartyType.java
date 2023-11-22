package com.cob.billing.enums;

public enum ReferringPartyType {
    DOCTOR("doctor"),
    FACILITY("Facility");
    private final String value;

    ReferringPartyType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
