package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PersistCMSFileUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    public void persist(InvoiceRequest invoiceRequest) throws IOException {

    }
}
