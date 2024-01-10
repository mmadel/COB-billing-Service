package com.cob.billing.model.clinical.patient.insurance;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsurance {

    private Long id;
    private String relation;
    private PatientRelation patientRelation;
    private PatientInsurancePolicy patientInsurancePolicy;
    private PatientInsuranceAdvanced patientInsuranceAdvanced;
    private Boolean isArchived;
    private InsuranceCompanyVisibility visibility;
    /*
            [0] name
            [1] payer id
     */
    private String[] insuranceCompany;
    private BasicAddress insuranceCompanyAddress;
}
