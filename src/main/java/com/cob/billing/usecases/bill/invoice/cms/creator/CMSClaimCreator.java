package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CMSClaimCreator {
    Map<String,List<SelectedSessionServiceLine>> create(InvoiceRequest invoiceRequest , Boolean[] flags) throws IOException, IllegalAccessException;
}
