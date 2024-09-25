package com.cob.billing.model.clinical.patient.update.profile;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientSessionDTO {
    private int operation;
    private PatientSession patientSession;
}
