package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.entity.bill.balance.PatientBalanceAccountSettings;
import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.cob.billing.model.bill.posting.balance.ClientBalanceProvider;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ProviderTableCreator;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class CreateProviderTableUseCase {
    private Document document;

    public void createTable(ClientBalanceInvoice clientBalanceInvoice) throws IOException {
        List<ClientBalanceProvider> providers = findProviders(clientBalanceInvoice);
        ProviderTableCreator providerTableCreator = new ProviderTableCreator(providers);
        providerTableCreator.create();
        document.add(providerTableCreator.table);
    }

    public void setDocument(Document document) {
        if (document == null)
            throw new IllegalArgumentException("Document can't be null");
        this.document = document;
    }

    private List<ClientBalanceProvider> findProviders(ClientBalanceInvoice clientBalanceInvoice) {
        List<ClientBalancePayment> paymentsCollections = new ArrayList<>();
        if (clientBalanceInvoice.getFinalizedClientBalance() != null)
            paymentsCollections.addAll(clientBalanceInvoice.getFinalizedClientBalance());
        if (clientBalanceInvoice.getPendingClientBalance() != null)
            paymentsCollections.addAll(clientBalanceInvoice.getPendingClientBalance());
        return paymentsCollections.stream()
                .map(ClientBalancePayment::getClientBalanceAccount)
                .collect(Collectors.groupingBy(ClientBalanceAccount::getProviderNPI))
                .values()
                .stream()
                .map(clientBalanceAccounts -> {
                    ClientBalanceProvider clientBalanceProvider = new ClientBalanceProvider();
                    for (ClientBalanceAccount clientBalanceAccount : clientBalanceAccounts) {
                        clientBalanceProvider.setId(new Random().nextInt(900000) + 100000);
                        clientBalanceProvider.setName(clientBalanceAccount.getProvider());
                        clientBalanceProvider.setNpi(clientBalanceAccount.getProviderNPI());
                        clientBalanceProvider.setLicenseNumber(clientBalanceAccount.getProviderLicenseNumber());
                    }
                    return clientBalanceProvider;
                })
                .distinct()
                .collect(Collectors.toList());
    }
}
