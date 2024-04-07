package com.cob.billing.model.bill.posting.paymnet;

import com.cob.billing.enums.ServiceLinePaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ServiceLinePaymentRequest {
    private Long id;

    private ServiceLinePaymentType serviceLinePaymentType;

    private Long paymentEntityId;

    private Long totalAmount;

    private String paymentMethod;

    private Long receivedDate;

    private Long checkDate;

    private Long checkNumber;

    private Long depositDate;

    private String insuranceCompany;

    private List<SessionServiceLinePayment> serviceLinePayments;

}
