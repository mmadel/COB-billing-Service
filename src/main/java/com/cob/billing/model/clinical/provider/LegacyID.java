package com.cob.billing.model.clinical.provider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LegacyID {
    private String providerId;
    private String providerIdQualifier;
    private String payerName;
}
