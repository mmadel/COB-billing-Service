package com.cob.billing.model.bill.posting;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.SessionAction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BatchServiceLinePayment {

    private Long sessionId;
    private Long serviceLineId;
    private Long dateOfService;
    private String cpt;
    private String provider;
    private double billedValue;
    private double previousPayments;
    private Double payment;
    private Double adjust;
    private double balance;
    private ServiceLinePaymentAction serviceLinePaymentAction;
}
