package com.cob.billing.model.admin;

import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.security.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Organization {
    private Long id;
    private String businessName;
    private String firstName;
    private String lastName;
    private String npi;
    private OrganizationData organizationData;
    private OrganizationType type;
    private UserAccount user;
    private List<Clinic> clinics;
}
