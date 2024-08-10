package com.cob.billing.usecases.bill.invoice.provider;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

public class ElectronicClaimProvider extends ClaimProvider{
    InvoiceRequest invoiceRequest;
    public ElectronicClaimProvider(InvoiceRequest invoiceRequest) {
        this.invoiceRequest = invoiceRequest;
    }

    @Override
    public void create() {
    }

    @Override
    public void submit() {

    }
}
