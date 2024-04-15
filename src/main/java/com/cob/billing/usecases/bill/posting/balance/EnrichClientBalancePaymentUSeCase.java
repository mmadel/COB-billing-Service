package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrichClientBalancePaymentUSeCase {
    public void enrichWithLOC(List<ClientBalanceAccount> clientBalanceAccounts, List<ClientBalancePayment> clientBalancePayments) {

        clientBalancePayments.stream()
                .forEach(clientBalancePayment -> {
                    for (int i = 0; i < clientBalanceAccounts.size(); i++) {
                        if (clientBalancePayment.getClientBalanceAccount().equals(clientBalanceAccounts.get(i))){
                            clientBalancePayment.setLoc(clientBalanceAccounts.get(i).getLoc());
                        }

                    }
                });
    }
}
