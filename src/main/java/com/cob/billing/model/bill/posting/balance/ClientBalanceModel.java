package com.cob.billing.model.bill.posting.balance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
public class ClientBalanceModel {
    private Long sessionId;
    private Long serviceLineId;
}
