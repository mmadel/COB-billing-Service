package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.response.InvoiceResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class BillingClaim {
    InvoiceRequest invoiceRequest;
    InvoiceResponse invoiceResponse;
    Boolean[] flags;
    public final void submit(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.invoiceRequest = invoiceRequest;
        this.invoiceResponse = new InvoiceResponse();
        pickClaimProvider();
        createClaim();
        submitClaim();
    }

    protected abstract void pickClaimProvider();
    protected abstract void createClaim() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    protected abstract void submitClaim() throws IOException, IllegalAccessException;
    public InvoiceResponse getInvoiceResponse(){
        return invoiceResponse;
    }

}
