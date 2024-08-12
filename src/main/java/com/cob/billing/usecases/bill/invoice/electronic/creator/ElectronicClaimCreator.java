package com.cob.billing.usecases.bill.invoice.electronic.creator;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.integration.claimmd.Claim;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ElectronicClaimCreator {
     List<Claim> create(InvoiceRequest invoiceRequest, Boolean[] flags) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
