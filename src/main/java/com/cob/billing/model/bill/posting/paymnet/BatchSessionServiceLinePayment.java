package com.cob.billing.model.bill.posting.paymnet;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatchSessionServiceLinePayment {
    private Long serviceLineId;
    private Long dos;
    private String cpt;
    private String provider;
    private double balance;
    private Double payment;
    private Double adjust;
    private double previousPayment;
    private double charge;
    private ServiceLinePaymentAction serviceLinePaymentAction;
    private Long createdAt;
}
