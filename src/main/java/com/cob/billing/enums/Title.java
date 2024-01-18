package com.cob.billing.enums;

public enum Title {
    Mr("Mr."),
    Ms("Ms."),
    Mrs("Mrs."),
    Miss("Miss."),
    Dr("Dr.");

    public final String label;

    Title(String label) {
        this.label = label;
    }
}
