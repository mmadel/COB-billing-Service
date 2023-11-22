package com.cob.billing.enums;

public enum PaymentType {
    COPAY("Copay"),
    COINSURANCE("Coinsurance");
    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
