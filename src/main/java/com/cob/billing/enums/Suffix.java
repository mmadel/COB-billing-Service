package com.cob.billing.enums;

public enum Suffix {
    Jr("jr"),
    Sr("sr"),
    II("ii"),
    III("iii"),
    IV("iv");

    public final String label;

    Suffix(String label) {
        this.label = label;
    }
}
