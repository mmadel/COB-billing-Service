package com.cob.billing.enums;

public enum Gender {
    Male("M"),
    Female("F"),
    NonBinary("U"),
    SelfDescribe("U"),
    NotPrefer("U");
    private String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
