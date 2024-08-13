package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class BillingClaim {
    InvoiceRequest invoiceRequest;
    Boolean[] flags;
    public final void submit(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.invoiceRequest = invoiceRequest;
        pickClaimProvider();
        createClaim();
        submitClaim();
    }

    protected abstract void pickClaimProvider();
    protected abstract void createClaim() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    protected abstract void submitClaim() throws IOException;

}
