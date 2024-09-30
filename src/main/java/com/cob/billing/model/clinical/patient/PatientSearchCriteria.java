package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientSearchCriteria {
    private String name;
    private String email;
    private String insuranceCompany;
    private String provider;
    private String clinic;
}
