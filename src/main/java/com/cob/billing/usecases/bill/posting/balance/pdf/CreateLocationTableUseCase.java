package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.model.bill.posting.balance.*;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.LocationTableCreator;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class CreateLocationTableUseCase {
    public List<ClientBalanceLocation> createTable(Document document,
                                                   ClientBalanceInvoice clientBalanceInvoice,
                                                   PatientBalanceSettings patientBalanceSettings) throws IOException {
        List<ClientBalanceLocation> clientBalanceLocations = createLocationsRecords(clientBalanceInvoice);

        boolean[] settings = new boolean[]{patientBalanceSettings.getPatientBalanceAccountSettings().isLocation()
                , patientBalanceSettings.getPatientBalanceAccountSettings().isIcdCodes()};
        LocationTableCreator locationTableCreator = new LocationTableCreator(clientBalanceLocations, settings);
        locationTableCreator.create();
        document.add(locationTableCreator.table);
        return clientBalanceLocations;
    }

    private List<ClientBalanceLocation> createLocationsRecords(ClientBalanceInvoice clientBalanceInvoice) {
        List<ClientBalancePayment> paymentsCollections = new ArrayList<>();
        if (clientBalanceInvoice.getFinalizedClientBalance() != null)
            paymentsCollections.addAll(clientBalanceInvoice.getFinalizedClientBalance());
        if (clientBalanceInvoice.getPendingClientBalance() != null)
            paymentsCollections.addAll(clientBalanceInvoice.getPendingClientBalance());
        List<ClientBalanceAccount> clientBalanceAccounts = paymentsCollections.stream()
                .map(ClientBalancePayment::getClientBalanceAccount)
                .collect(Collectors.groupingBy(ClientBalanceAccount::getFacilityName))
                .values()
                .stream()
                .map(list -> list.get(0))
                .distinct()
                .collect(Collectors.toList());
        Map<String, List<ClientBalanceAccount>> repeatedCasesMap = paymentsCollections.stream()
                .collect(Collectors.groupingBy(account -> account.getClientBalanceAccount().getFacilityName(),
                        Collectors.mapping(account -> account.getClientBalanceAccount(), Collectors.toList())));
        AtomicInteger locationId = new AtomicInteger();
        return repeatedCasesMap.entrySet().stream()
                .map(entry -> {
                    locationId.set(locationId.get() + 1);
                    ClientBalanceLocation clientBalanceLocation = new ClientBalanceLocation();
                    clientBalanceLocation.setLocationName(entry.getKey());
                    clientBalanceLocation.setId(locationId.get());
                    List<ClientBalanceAccount> accounts = entry.getValue().stream()
                            .distinct()
                            .collect(Collectors.toList());
                    clientBalanceLocation.setLocationAddress(accounts.stream()
                            .findFirst().get().getFacilityAddress());
                    clientBalanceLocation.setClientName(accounts.stream()
                            .findFirst().get().getClientName());
                    List<String> cases = new ArrayList<>();
                    List<String> icdCodes = new ArrayList<>();
                    for (ClientBalanceAccount clientBalanceAccount : accounts) {
                        cases.add(clientBalanceAccount.getCaseTitle());
                        icdCodes.add(clientBalanceAccount.getIcdCodes());
                    }
                    clientBalanceLocation.setCases(cases);
                    clientBalanceLocation.setIcdCodes(icdCodes);
                    return clientBalanceLocation;
                })
                .collect(Collectors.toList());
    }
}
