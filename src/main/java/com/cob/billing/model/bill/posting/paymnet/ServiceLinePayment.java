package com.cob.billing.model.bill.posting.paymnet;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceLinePayment {
    private Long id;

    private double balance;

    private double payment;

    private double adjust;

    private ServiceLinePaymentAction serviceLinePaymentAction;

    private ServiceLinePaymentType serviceLinePaymentType;

    private Long serviceLineId;


}
