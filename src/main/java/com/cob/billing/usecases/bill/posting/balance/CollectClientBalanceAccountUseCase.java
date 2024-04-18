package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.bill.posting.balance.ClientBalanceLocation;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CollectClientBalanceAccountUseCase {
    public List<ClientBalanceAccount> collect(ClientBalanceInvoice clientBalanceInvoice) {
        List<ClientBalancePayment> paymentsCollections = new ArrayList<>();
        paymentsCollections.addAll(clientBalanceInvoice.getFinalizedClientBalance());
        paymentsCollections.addAll(clientBalanceInvoice.getPendingClientBalance());
        List<ClientBalanceAccount> clientBalanceAccounts = paymentsCollections.stream()
                .map(ClientBalancePayment::getClientBalanceAccount)
                .collect(Collectors.groupingBy(ClientBalanceAccount::getSessionId))
                .values()
                .stream()
                .map(list -> list.get(0))
                .distinct()
                .collect(Collectors.toList());

        int counter = 0;
        for (int i = 0; i < clientBalanceAccounts.size(); i++) {
            counter = counter + 1;
            clientBalanceAccounts.get(i).setLoc(counter + "");
        }
        return clientBalanceAccounts;
    }
}
