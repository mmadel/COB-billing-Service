package com.cob.billing.model.clinical.patient.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAuthorization {
    private String authNumber;
    private String[] insCompany;
    private String serviceCode;
    private Long startDateNumber;
    private Long expireDateNumber;
    private Integer visit;
    private Integer remaining;
    private Long patientId;
    private Boolean isExpired;
}
