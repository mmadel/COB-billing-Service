package com.cob.billing.model.bill.posting.balance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientBalanceProvider extends ClientBalanceModel {
    private Integer id;
    private String name;
    private String npi;
    private String LicenseNumber;
}
