package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseDiagnosis {
    private String diagnosisCode;
    private String diagnosisDescription;
}
