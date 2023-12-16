package com.cob.billing.model.bill.posting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PaymentServiceLine {

    private Long sessionId;
    private Long ServiceCodeId;
    private Long dateOfService;
    private String cpt;
    private String provider;
    private Integer billedValue;

}
