package com.cob.billing.model.bill.invoice.tmp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequest {
    private InvoicePatientInformation patientInformation;
    private InvoicePatientInsuredInformation invoicePatientInsuredInformation;
    private InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation ;
    private InvoiceBillingProviderInformation invoiceBillingProviderInformation;
}
