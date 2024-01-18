package com.cob.billing.model.admin;

import com.cob.billing.enums.OrganizationType;
import lombok.Getter;
import lombok.Setter;

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
}
