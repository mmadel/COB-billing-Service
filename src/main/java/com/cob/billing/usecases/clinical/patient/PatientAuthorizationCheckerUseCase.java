package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
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
        List<Long[]> dates = invoiceRequest.getPatientInformation().getAuthorizationDates();
        List<Long> patientAuthorizationsToBeDecrements;
        String authNumber = "";
        if (dates.size() == 1)
            patientAuthorizationsToBeDecrements = checkPatientAuthorization(dates.get(0), invoiceRequest.getSelectedSessionServiceLine());
        else
            patientAuthorizationsToBeDecrements = checkPatientAuthorizations(dates, invoiceRequest.getSelectedSessionServiceLine());

        if (!patientAuthorizationsToBeDecrements.isEmpty())
            authNumber = updateSinglePatientAuthorization(patientAuthorizationsToBeDecrements);
        if (authNumber != "")
            invoiceRequest.getPatientInformation().setAuthNumber(authNumber);
    }

    private List<Long> checkPatientAuthorization(Long[] dates, List<SelectedSessionServiceLine> serviceLines) {
        List<Long> patientAuthorizations = new ArrayList<>();
        List<PatientSession> sessions = serviceLines.stream().map(serviceLine -> serviceLine.getSessionId()).collect(Collectors.toList());
        sessions.forEach(patientSession -> {
            if (patientSession.getServiceDate() >= dates[0] && patientSession.getServiceDate() <= dates[1])
                patientAuthorizations.add(dates[2]);
        });
        return patientAuthorizations;
    }

    private List<Long> checkPatientAuthorizations(List<Long[]> dates, List<SelectedSessionServiceLine> serviceLines) throws IllegalAccessException {
        List<Long> patientAuthorizations = new ArrayList<>();
        dates.forEach(date -> {
            patientAuthorizations.addAll(checkPatientAuthorization(date, serviceLines));
        });
        if (patientAuthorizations.size() > 1)
            throw new IllegalAccessException("Over Lapping");
        return patientAuthorizations;
    }

    private String updateSinglePatientAuthorization(List<Long> authIds) {
        AtomicReference<String> authNumber = new AtomicReference<>("");
        List<PatientAuthorizationEntity> toBeUpdate = new ArrayList<>();
        patientAuthorizationRepository.findAllById(authIds).forEach(patientAuthorizationEntity -> {
            authNumber.set(patientAuthorizationEntity.getAuthNumber());
            int remaining = patientAuthorizationEntity.getRemaining() - 1;
            patientAuthorizationEntity.setRemaining(remaining);
            toBeUpdate.add(patientAuthorizationEntity);
        });
        patientAuthorizationRepository.saveAll(toBeUpdate);
        return authNumber.get();
    }
}
