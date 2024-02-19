package com.cob.billing.model.clinical.patient.advanced;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAdvancedDates {
    private Long firstSymptoms;
    private Long lastSeenByDoctor;
    private Long accident;
    private Long firstTreatment;
}
