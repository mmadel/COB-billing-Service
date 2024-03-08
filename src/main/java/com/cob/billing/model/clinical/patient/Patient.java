package com.cob.billing.model.clinical.patient;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.Gender;
import com.cob.billing.enums.MaritalStatus;
import com.cob.billing.model.clinical.patient.advanced.PatientAdvancedInformation;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
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

    private Address address;

    private List<PatientCase> cases;
    private ReferringProvider referringProvider;
    private List<PatientInsurance> patientInsurances;
    private List<PatientSession> sessions;
    private String phoneType;
    private String phone;
    private String email;
    private String copay;
    private String ssn;
    private String externalId;
    private PatientAdvancedInformation patientAdvancedInformation;
    private Boolean authTurnOff;
}
