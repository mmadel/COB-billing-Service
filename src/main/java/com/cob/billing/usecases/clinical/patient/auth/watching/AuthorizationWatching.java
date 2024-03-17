package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthorizationWatching {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    AuthorizationSessionSubmission authorizationSessionSubmission;

    public void watch(InvoiceRequest invoiceRequest) throws AuthorizationException {
        if (invoiceRequest.getPatientInformation().getAuthorizationWatching()) {
            for (SelectedSessionServiceLine serviceLine : invoiceRequest.getSelectedSessionServiceLine()) {
                ;
                authorizationSessionSubmission.submit(SubmissionSession.builder()
                        .patientSession(serviceLine.getSessionId())
                        .insuranceCompanyId(invoiceRequest.getInvoiceInsuranceCompanyInformation().getId())
                        .build());
            }
        } else {
            List<Long> sessionIds = getSessionIds(invoiceRequest.getSelectedSessionServiceLine());

            Map<Long, String> mapping = mapSessionToAuthorizationNumber(sessionIds);

            invoiceRequest.getSelectedSessionServiceLine().forEach(serviceLine -> {
                String authNumber = mapping.get(serviceLine.getSessionId().getId());
                if (authNumber != null)
                    serviceLine.getSessionId().setAuthorization(authNumber);
            });
        }
    }

    private List<Long> getSessionIds(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        return selectedSessionServiceLines.stream()
                .map(serviceLine -> {
                    return serviceLine.getSessionId().getId();
                }).collect(Collectors.toList());
    }

    private Map<Long, String> mapSessionToAuthorizationNumber(List<Long> sessionIds) {
        Map<Long, String> sessionAuthorizationNumberMapping = new HashMap<>();
        patientSessionRepository.findAllById(sessionIds).forEach(patientSessionEntity -> {
            if (patientSessionEntity.getPatientAuthorization() != null)
                sessionAuthorizationNumberMapping.put(patientSessionEntity.getId(), patientSessionEntity.getPatientAuthorization().getAuthNumber());
        });
        return sessionAuthorizationNumberMapping;
    }
}
