package com.cob.billing.model.clinical.patient.insurance;

import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsurance {

    private Long id;
    private String relation;
    private BasicAddress payerAddress;
    private PatientRelation patientRelation;
    private PatientInsurancePolicy patientInsurancePolicy;
    private PatientInsuranceAdvanced patientInsuranceAdvanced;
    private Boolean isArchived;
    private Long insuranceCompany;
}
