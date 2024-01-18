package com.cob.billing.model.clinical.patient.insurance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsuranceAdvanced {
    private Boolean acceptAssigment;
    private Boolean signatureOnFile;
    private String informationRelease;
}
