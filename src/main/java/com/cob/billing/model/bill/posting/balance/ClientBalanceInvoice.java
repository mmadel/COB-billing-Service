package com.cob.billing.model.bill.posting.balance;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientBalanceInvoice {
    private String providerName;
    private String providerAddress;
    private Long invoiceDate;
    private String invoiceNumber;

    private String clientName;
    private String clientAddress;

    private String clinicName;
    private List<ClientBalance> finalizedClientBalance;
    private List<ClientBalance> pendingClientBalance;
}
