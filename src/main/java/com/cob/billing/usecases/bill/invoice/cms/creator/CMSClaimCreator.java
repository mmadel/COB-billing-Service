package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.bill.invoice.request.InvoiceRequest;

import java.io.IOException;
import java.util.List;

public interface CMSClaimCreator {
    List<String> create(InvoiceRequest invoiceRequest , Boolean[] flags) throws IOException, IllegalAccessException;
}
