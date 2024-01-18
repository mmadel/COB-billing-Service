package com.cob.billing.model.clinical.patient.insurance;

import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRelation {
    private String r_firstName;
    private String r_middleName;
    private String r_lastName;
    private long r_birthDate;
    private String r_gender;
    private Address r_address;
    private String r_phone;
}
