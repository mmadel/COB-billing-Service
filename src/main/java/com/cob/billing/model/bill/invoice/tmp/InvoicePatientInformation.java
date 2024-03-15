package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.enums.Gender;
import com.cob.billing.model.bill.invoice.tmp.auth.AuthorizationInformation;
import com.cob.billing.model.bill.invoice.tmp.auth.AuthorizationSelection;
import com.cob.billing.model.clinical.patient.advanced.PatientAdvancedInformation;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoicePatientInformation {
    private Long id;
    private String firstName;
    private String lastName;
    private Long dateOfBirth;
    private Gender gender;
    private Address address;
    private String phone;
    private PatientAdvancedInformation patientAdvancedInformation;
    private String ssn;
    private String externalId;
    private String box26;
    private ReferringProvider referringProvider;
    private String insuredPrimaryId;
}
