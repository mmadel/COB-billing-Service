package com.cob.billing.model.clinical.patient.insurance;

import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRelation {
    private String firstName;
    private String middleName;
    private String lastName;
    private long birthDate;
    private String gender;
    private Address address;
    private String phone;
}
