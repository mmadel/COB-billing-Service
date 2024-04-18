package com.cob.billing.model.bill.posting.balance;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientBalanceLocation extends ClientBalanceModel {
    private Integer id;
    private String locationName;
    private String locationAddress;
    List<String> cases;
    List<String> icdCodes;
    private String clientName;

}
