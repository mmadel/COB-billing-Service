package com.cob.billing.model.clinical.patient.update.profile;

import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInsuranceDTO {
    private int operation;
    private PatientInsurance patientInsurance;
}
