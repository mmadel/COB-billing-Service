package com.cob.billing.usecases.bill.invoice.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CarrierInformationModel {
    private String insurance_name;
    private String insurance_address;
    private String insurance_address2;
    private String insurance_city_state_zip;
    private String insurance_type;
    private String insurance_id;
}
