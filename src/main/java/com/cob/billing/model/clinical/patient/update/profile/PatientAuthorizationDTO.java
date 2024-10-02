package com.cob.billing.model.clinical.patient.update.profile;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAuthorizationDTO {
    private int operation;
    private PatientAuthorization patientAuthorization;
}
