package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.enums.Gender;
import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoicePatientInsuredInformation {
    private String primaryId;
    private String relationToInsured;
    private String firstName;
    private String lastName;
    private Long dateOfBirth;
    private Gender gender;
    private Address address;
    private String phone;
}
