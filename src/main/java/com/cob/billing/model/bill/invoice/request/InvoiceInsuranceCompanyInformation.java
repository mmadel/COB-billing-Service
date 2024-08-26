package com.cob.billing.model.bill.invoice.request;

import com.cob.billing.model.bill.invoice.tmp.OtherPatientInsurance;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoiceInsuranceCompanyInformation {
    private String name;
    private Long id;
    private BasicAddress address;
    private InsuranceCompanyVisibility visibility;
    private String[] assigner; /* payerId , payerName , payerAddress*/
    private Boolean isAssignment;
    private String signature;
    private String insuranceType;
    private Integer numberOfActivePatientInsurances;
    private String[] policyInformation;
    private List<OtherPatientInsurance> otherInsurances;
}
