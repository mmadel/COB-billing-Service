package com.cob.billing.model.bill.posting.paymnet;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceLinePayment {

    public ServiceLinePayment(double balance, double payment, double adjust , Long serviceLineId,Long createdAt) {
        this.balance = balance;
        this.payment = payment;
        this.adjust = adjust;
        this.serviceLineId = serviceLineId;
        this.createdAt = createdAt;
    }

    private Long id;

    private double balance;

    private double payment;

    private double adjust;

    private ServiceLinePaymentAction serviceLinePaymentAction;

    private Long serviceLineId;
    private Long createdAt;

}
