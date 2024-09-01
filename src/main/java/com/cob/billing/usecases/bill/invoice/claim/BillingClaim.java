package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.response.tmp.InvoiceResponse;
import com.cob.billing.model.clinical.patient.session.PatientSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BillingClaim {
    InvoiceRequest invoiceRequest;
    InvoiceResponse invoiceResponse;
    Boolean[] flags;

    public final void submit(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.invoiceRequest = invoiceRequest;
        pickClaimProvider();
        prepareInvoiceResponse();
        createClaim();
        submitClaim();
    }

    protected abstract void pickClaimProvider();

    protected abstract void createClaim() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    protected abstract void submitClaim() throws IOException, IllegalAccessException;

    public InvoiceResponse getInvoiceResponse() {
        return invoiceResponse;
    }

    private void prepareInvoiceResponse() {
        this.invoiceResponse = new InvoiceResponse();
        invoiceResponse.setSessions(invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId())));
    }

}
