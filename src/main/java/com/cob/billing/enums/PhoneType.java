package com.cob.billing.enums;

public enum PhoneType {
    Home("home"),
    Work("work"),
    Cell_Phone("cell"),
    Other("other");
    private final String value;

    PhoneType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
