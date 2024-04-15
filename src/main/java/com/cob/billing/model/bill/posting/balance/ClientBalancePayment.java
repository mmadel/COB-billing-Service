package com.cob.billing.model.bill.posting.balance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientBalancePayment {
    private Long dos;
    private String serviceCode;
    private String provider;
    private Double charge;
    private Double insCompanyPayment;
    private Double clientPayment;
    private Double adjustPayment;
    private Double balance;
    private String placeOfCode;
    private Integer units;
    private ClientBalanceAccount clientBalanceAccount;
}
