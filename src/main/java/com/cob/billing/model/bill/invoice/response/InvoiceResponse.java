package com.cob.billing.model.bill.invoice.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoiceResponse {
    List<ClaimSubmission> claimSubmissions;
}
