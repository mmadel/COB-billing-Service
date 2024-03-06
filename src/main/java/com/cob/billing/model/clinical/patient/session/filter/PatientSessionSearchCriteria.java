package com.cob.billing.model.clinical.patient.session.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientSessionSearchCriteria {
    private String provider;
    private String sessionCase;
    private Long startDate;
    private Long endDate;
}
