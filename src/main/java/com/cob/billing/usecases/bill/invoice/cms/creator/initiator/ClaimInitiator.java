package com.cob.billing.usecases.bill.invoice.cms.creator.initiator;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

import java.io.IOException;
import java.util.List;

public abstract class ClaimInitiator {
    public abstract List<String> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException;
}
