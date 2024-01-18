package com.cob.billing.model.bill.invoice;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoiceRequestCreation {
    private List<SelectedSessionServiceLine> selectedSessionServiceLines;
    private Boolean isOneDateServicePerClaim;
    private String delayedReason;
    private Long patientId;
    private InsuranceCompanyVisibility visibility;
    /*
            [0] name
            [1] payer id
     */
    private String[] insuranceCompany;
}
