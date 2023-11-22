package com.cob.billing.model.clinical.patient;

import com.cob.billing.enums.Gender;
import com.cob.billing.enums.MaritalStatus;
import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Patient {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Long birthDate;

    private MaritalStatus maritalStatus;

    private Gender gender;

    private List<Address> addresses;

    private List<PatientCase> cases;
}
