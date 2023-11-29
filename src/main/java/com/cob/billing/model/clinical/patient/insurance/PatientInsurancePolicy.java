package com.cob.billing.model.clinical.patient.insurance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsurancePolicy {
    private String insuranceCompnayName;
    private String responsability;
    private String planType;
    private String primaryId;
    private String policyGroup;
    private String plan;
    private String employer;
}
