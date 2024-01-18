package com.cob.billing.model.clinical.referring.provider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferringProvider {
    private Long id;
    private String firstName;
    private String lastName;
    private String npi;

    public boolean isEmpty() {
        return firstName == "" && lastName == "" && npi == "";
    }
}
