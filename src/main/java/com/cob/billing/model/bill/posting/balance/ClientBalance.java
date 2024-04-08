package com.cob.billing.model.bill.posting.balance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientBalance {
    private Long dos;
    private String serviceCode;
    private String provider;
    private Double charge;
    private Double insCompanyPayment;
    private Double clientPayment;
    private Double adjustPayment;
    private Double balance;
}
