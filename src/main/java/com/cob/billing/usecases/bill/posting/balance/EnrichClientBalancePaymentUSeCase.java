package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceLocation;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrichClientBalancePaymentUSeCase {
    public void enrichWithLOC(List<ClientBalanceLocation> clientBalanceLocations, List<ClientBalancePayment> clientBalancePayments) {

        clientBalancePayments.stream()
                .forEach(clientBalancePayment -> {
                    for (int i = 0; i < clientBalanceLocations.size(); i++) {
                        if (clientBalancePayment.getClientBalanceAccount().getFacilityName().equals(clientBalanceLocations.get(i).getLocationName())) {
                            clientBalancePayment.setLoc(clientBalanceLocations.get(i).getId() + "");
                        }

                    }
                });
    }
}
