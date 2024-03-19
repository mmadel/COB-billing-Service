package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import org.springframework.stereotype.Component;

@Component
public class HandlePatientAuthorizationUseCase {
    public void handle(InvoiceRequest invoiceRequest){
        switch (invoiceRequest.getPatientInformation().getPatientAuthorizationWatching()){
            case TurnOn:
                System.out.println("Patient authorization is turned ON");
                break;
            case TurnOff:
                System.out.println("Patient authorization is turned OFF");
                break;
        }
    }
}
