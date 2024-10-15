package com.cob.billing.enums;

public enum GenderIdentity {
    NonBinary("NonBinary"),
    SelfDescribe("SelfDescribe"),
    NotPrefer("NotPrefer"),
    Other("Other");
    private String value;

    private GenderIdentity(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
