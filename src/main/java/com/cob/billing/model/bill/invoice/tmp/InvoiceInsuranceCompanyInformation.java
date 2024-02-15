package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceInsuranceCompanyInformation {
    private String name;
    private BasicAddress address;
    private InsuranceCompanyVisibility visibility;
    private String[] assigner; /* payerId , payerName , payerAddress*/
    private Boolean isAssignment;
    private String signature;
}
