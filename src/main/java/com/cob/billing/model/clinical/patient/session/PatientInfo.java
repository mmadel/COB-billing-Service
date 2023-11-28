package com.cob.billing.model.clinical.patient.session;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientInfo {
    private Long patientId;
    private String patientFirstName;
    private String patientMiddleName;
    private String patientLastName;
}
