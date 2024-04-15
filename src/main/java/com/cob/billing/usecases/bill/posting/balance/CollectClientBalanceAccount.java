package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectClientBalanceAccount {
    public List<ClientBalanceAccount> collect(ClientBalanceInvoice clientBalanceInvoice) {
        List<ClientBalancePayment> paymentsCollections = new ArrayList<>();
        paymentsCollections.addAll(clientBalanceInvoice.getFinalizedClientBalance());
        paymentsCollections.addAll(clientBalanceInvoice.getPendingClientBalance());
        return paymentsCollections.stream()
                .map(ClientBalancePayment::getClientBalanceAccount)
                .collect(Collectors.groupingBy(ClientBalanceAccount::getSessionId))
                .values()
                .stream()
                .map(list -> list.get(0))
                .distinct()
                .collect(Collectors.toList());
    }
}
