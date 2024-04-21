package com.cob.billing.model.bill.posting.balance;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientBalanceInvoice {
    List<ClientBalancePayment> finalizedClientBalance;
    List<ClientBalancePayment>  pendingClientBalance;
}
