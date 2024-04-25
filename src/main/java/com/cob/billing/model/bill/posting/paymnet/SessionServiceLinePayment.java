package com.cob.billing.model.bill.posting.paymnet;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionServiceLinePayment {

    public SessionServiceLinePayment(double balance, double payment, double adjust , Long serviceLineId, Long createdAt, ServiceLinePaymentType serviceLinePaymentType) {
        this.balance = balance;
        this.payment = payment;
        this.adjust = adjust;
        this.serviceLineId = serviceLineId;
        this.createdAt = createdAt;
        this.serviceLinePaymentType = serviceLinePaymentType;
    }

    private Long id;

    private double balance;

    private double payment;

    private double adjust;

    private ServiceLinePaymentAction serviceLinePaymentAction;
    private ServiceLinePaymentType serviceLinePaymentType;
    private BatchServiceLineData batchServiceLineData;
    private Long serviceLineId;
    private Long sessionId;
    private Long createdAt;

}
