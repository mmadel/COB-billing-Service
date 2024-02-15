package com.cob.billing.usecases.bill.invoice.electronic.enums;

public enum BillingProviderTaxIDType {
    EIN("E"),
    SIN("S");
    private String value;

    private BillingProviderTaxIDType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
