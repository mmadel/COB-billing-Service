package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientSearchCriteria {
    private String name;
    private String phone;
    private String email;
    private String insuranceCompany;
    private String provider;
    private String clinic;

    @Override
    public String toString() {
        return "PatientSearchCriteria{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", insuranceCompany='" + insuranceCompany + '\'' +
                ", provider='" + provider + '\'' +
                ", clinic='" + clinic + '\'' +
                '}';
    }
}
