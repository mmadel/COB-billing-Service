package com.cob.billing.model.bill.posting;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentBatch {
    private Long totalAmount;
    private String paymentMethod;
    private Long receivedDate;
    private Long checkDate;

    private Long checkNumber;
    private Long depositDate;
    private PostingInsuranceCompany insuranceCompany;


}
