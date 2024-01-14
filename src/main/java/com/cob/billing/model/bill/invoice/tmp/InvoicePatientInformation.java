package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.enums.Gender;
import com.cob.billing.model.clinical.patient.advanced.PatientAdvancedInformation;
import com.cob.billing.model.common.BasicAddress;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoicePatientInformation {
    private String firstName;
    private String lastName;
    private Long dateOfBirth;
    private Gender gender;
    private BasicAddress address;
    private String phone;
    private PatientAdvancedInformation patientAdvancedInformation;
    private String ssn;
    private String externalId;
    private  String box26;
}
