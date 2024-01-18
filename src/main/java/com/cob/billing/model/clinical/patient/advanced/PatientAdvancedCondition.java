package com.cob.billing.model.clinical.patient.advanced;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAdvancedCondition {
    private boolean employment;
    private boolean autoAccident;
    private boolean otherAccident;
    private String state;
}
