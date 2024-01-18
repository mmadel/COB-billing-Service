package com.cob.billing.model.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    private String type;
    private String first;
    private String second;
    private String country;
    private String state;
    private String province;
    private String city;
    private String zipCode;
}
