package com.cob.billing.model.clinical.patient.advanced;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAdvancedInformation {
    private PatientAdvancedCondition pateintAdvancedCondtion;
    private PatientAdvancedDates patientAdvancedDates;
    private Long unableToWorkStartDate;
    private Long unableToWorkEndDate;
    private Long hospitalizedStartDate;
    private Long hospitalizedEndDate;
    private String additionalInformation;
}
