package com.cob.billing.model.clinical.patient.insurance;

import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsurance {
    private String relation;
    private Address payerAddress;
    private PatientRelation patientRelation;
    private PatientInsurancePolicy patientInsurancePolicy;
    private PatientInsuranceAdvanced patientInsuranceAdvanced;
}
