package com.cob.billing.model.integration.nppes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    public String country_code;
    public String country_name;
    public String address_purpose;
    public String address_type;
    public String address_1;
    public String address_2;
    public String city;
    public String state;
    public String postal_code;
    public String telephone_number;
}
