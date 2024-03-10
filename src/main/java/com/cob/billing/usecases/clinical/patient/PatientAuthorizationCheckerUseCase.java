package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.usecases.clinical.patient.auth.claim.approval.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class PatientAuthorizationCheckerUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void check(InvoiceRequest invoiceRequest) throws IllegalAccessException {
        AuthorizationHandling turningHandling = new AuthorizationTurningHandling();
        AuthorizationHandling selectionHandling = new AuthorizationSelectionHandling();
        AuthorizationHandling expirationHandling = new AuthorizationExpirationHandling();
        AuthorizationHandling remainingHandling = new AuthorizationRemainingHandling();

        turningHandling.setNextHandler(selectionHandling);
        selectionHandling.setNextHandler(expirationHandling);
        expirationHandling.setNextHandler(remainingHandling);
        turningHandling.processRequest(invoiceRequest);
    }
}
