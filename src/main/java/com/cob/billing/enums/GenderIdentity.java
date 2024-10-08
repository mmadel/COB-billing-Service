package com.cob.billing.enums;

public enum GenderIdentity {
    NonBinary("U"),
    SelfDescribe("U"),
    NotPrefer("U");
    private String value;

    private GenderIdentity(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
