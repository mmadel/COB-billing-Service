package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

public interface AuthorizationHandling {
    void setNextHandler(AuthorizationHandling nextHandler);

    void processRequest(InvoiceRequest request);
}
