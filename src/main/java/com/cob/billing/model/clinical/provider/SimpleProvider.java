package com.cob.billing.model.clinical.provider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimpleProvider {
    private Long id;
    private String firstName;
    private String lastName;
    private String npi;
}
