package com.cob.billing.model.clinical.patient.update.profile;

import com.cob.billing.model.clinical.patient.PatientCase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientCaseDTO {
    private int operation;
    private PatientCase patientCase;
}
