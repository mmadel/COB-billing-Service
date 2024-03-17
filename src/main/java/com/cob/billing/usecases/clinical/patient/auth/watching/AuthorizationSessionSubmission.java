package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSessionSubmission {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    public void submit(PatientSession patientSession){

    }
}
