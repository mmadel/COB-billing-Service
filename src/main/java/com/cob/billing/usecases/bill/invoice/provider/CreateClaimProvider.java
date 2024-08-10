package com.cob.billing.usecases.bill.invoice.provider;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

public class CreateClaimProvider {
    public static ClaimProvider getInstance(InvoiceRequest invoiceRequest){
        switch (invoiceRequest.getSubmissionType()){
            case Print:
                return new PrintClaimProvider(invoiceRequest);
            case Electronic:
                return new ElectronicClaimProvider(invoiceRequest);
            default:
                throw new IllegalArgumentException("Unknown Submission Type");

        }
    }
}
