package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.model.bill.invoice.request.InvoiceRequest;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MultipleItemsChecker {
    private static Boolean[] flags;
    public static Boolean check(InvoiceRequest invoiceRequest){
        flags = getMultipleItems(invoiceRequest);
        return Arrays.stream(flags)
                .anyMatch(Boolean::booleanValue);
    }
    public static Boolean[] getMultipleFlags(){
        return flags;
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
