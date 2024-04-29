package com.cob.billing.model.bill.posting.paymnet.batch.pdf;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientBatchReceiptPatientInfo {
    private String patientName;
    private String address;
    private String city;
    private String state;
    private String zipCode;


}
