package com.cob.billing.model.bill.invoice.request;

import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class InvoiceRequest {
    private InvoicePatientInformation patientInformation;
    private InvoicePatientInsuredInformation invoicePatientInsuredInformation;
    private InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation ;
    private InvoiceBillingProviderInformation invoiceBillingProviderInformation;
    private InvoiceRequestConfiguration invoiceRequestConfiguration;
    private List<SelectedSessionServiceLine> selectedSessionServiceLine;
    private CorrectClaimInformation correctClaimInformation;
    private SubmissionType submissionType;
    private HttpServletResponse response;
    private List<Long> records;
    private Map<String, List<SelectedSessionServiceLine>> fileNamesServiceLinesMapper;

}
