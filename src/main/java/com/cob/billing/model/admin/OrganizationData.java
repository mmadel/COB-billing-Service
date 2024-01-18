package com.cob.billing.model.admin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationData {
    private String taxId;
    private String taxonomy;
    private String address;
    private String addressTwo;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private String fax;
    private String email;
}
