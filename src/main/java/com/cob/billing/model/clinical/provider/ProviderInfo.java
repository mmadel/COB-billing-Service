package com.cob.billing.model.clinical.provider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProviderInfo {
    private String professionAbbr;
    private String pqrs;
    private String taxonomyCode;
    private String credentials;
    private String group;
    private String taxId;
    private String license;
}
