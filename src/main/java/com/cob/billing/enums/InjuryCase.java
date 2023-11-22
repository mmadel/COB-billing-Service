package com.cob.billing.enums;

public enum InjuryCase {
    AUTOACCIDENT("Auto Accident"),
    EMPLOYMENT("Employment"),
    NONOFTHEABOVE("Non of the Above");

    public final String label;

    InjuryCase(String label) {
        this.label = label;
    }

}
