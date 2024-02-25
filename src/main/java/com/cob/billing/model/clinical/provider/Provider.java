package com.cob.billing.model.clinical.provider;

import com.cob.billing.model.common.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Provider {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String npi;

    private String phone;
    private Address address;

    private ProviderInfo providerInfo;

    private LegacyID legacyID;


}
