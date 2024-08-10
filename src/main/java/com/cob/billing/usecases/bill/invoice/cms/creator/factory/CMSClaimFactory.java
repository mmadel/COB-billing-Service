package com.cob.billing.usecases.bill.invoice.cms.creator.factory;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.initiator.ClaimInitiator;
import com.cob.billing.usecases.bill.invoice.cms.creator.initiator.MultipleClaimInitiator;
import com.cob.billing.usecases.bill.invoice.cms.creator.initiator.SingleClaimInitiator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CMSClaimFactory {
    public static ClaimInitiator getInstance(InvoiceRequest invoiceRequest) {
        Boolean[] flags = getMultipleItems(invoiceRequest);
        boolean hasMultipleItem = Arrays.stream(flags)
                .anyMatch(Boolean::booleanValue);
        if (!hasMultipleItem)
            return new SingleClaimInitiator();
        else
            return new MultipleClaimInitiator(flags);
    }

    private static Boolean[] getMultipleItems(InvoiceRequest invoiceRequest) {
        Boolean[] multipleItemsFlags=  { false, false, false, false, false };
        if (getMultipleProviders(invoiceRequest) > 1)
            multipleItemsFlags[0]=true;
        if (getMultipleClinics(invoiceRequest) > 1)
            multipleItemsFlags[1]=true;
        if (getMultipleCases(invoiceRequest) > 1)
            multipleItemsFlags[2]=true;
        if (getMultipleDates(invoiceRequest) >1)
            multipleItemsFlags[3]=true;
        if (checkAuthorization(invoiceRequest)>1)
            multipleItemsFlags[4]=true;
        return multipleItemsFlags;

    }

    private static int getMultipleProviders(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId().getDoctorInfo())).size();
    }

    private static int getMultipleClinics(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getClinic())).size();
    }

    private static int getMultipleCases(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getCaseTitle())).size();
    }

    private static int getMultipleDates(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getServiceDate())).size();
    }

    private static int checkAuthorization(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .filter(serviceLine -> serviceLine.getSessionId().getAuthorizationNumber() != null)
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getAuthorizationNumber())).size();
    }
}
