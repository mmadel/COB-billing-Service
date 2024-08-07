package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.model.bill.auth.util.AuthorizationMapper;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.selection.AuthorizationSelection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class HandlePatientAuthorizationUseCase {
    @Autowired
    AuthorizationSelection authorizationSelection;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    ModelMapper mapper;
    private List<SelectedSessionServiceLine> invoicesSessionServiceLines;
    private List<SubmissionSession> submissionSessions;

    public void handle(InvoiceRequest invoiceRequest) throws AuthorizationException {
        invoicesSessionServiceLines = invoiceRequest.getSelectedSessionServiceLine();
        submissionSessions = prepareSubmissionSessions(invoiceRequest.getInvoiceInsuranceCompanyInformation().getId(), invoiceRequest.getPatientInformation().getId());
        Map<Long , Integer> authorizationToUpdate = new HashMap<>();
        switch (invoiceRequest.getPatientInformation().getPatientAuthorizationWatching()) {
            case TurnOn:
                System.out.println("Patient authorization is turned ON");
                for (SubmissionSession submissionSession : submissionSessions) {
                    authorizationSelection.select(submissionSession);
                    PatientSession patientSession = pickPatientSession(submissionSession.getSessionId());
                    patientSession.setAuthorizationNumber(submissionSession.getAuthorizationSession().getAuthorizationNumber());
                    authorizationToUpdate.put(submissionSession.getAuthorizationSession().getId(),submissionSession.getAuthorizationSession().getRemainingValue());
                }
                updateAuthorization(authorizationToUpdate);
                break;
            case TurnOff:
                System.out.println("Patient authorization is turned OFF");
                submissionSessions
                        .forEach(submissionSession -> {
                            PatientSession patientSession = pickPatientSession(submissionSession.getSessionId());
                            if (patientSession != null && submissionSession.getAuthorizationSession() != null)
                                patientSession.setAuthorizationNumber(submissionSession.getAuthorizationSession().getAuthorizationNumber());
                        });
                break;
        }
    }

    private List<SubmissionSession> prepareSubmissionSessions(Long insuranceCompany, Long patientId) {
        List<Long> sessions = invoicesSessionServiceLines.stream()
                .map(serviceLine -> serviceLine.getSessionId().getId()).collect(Collectors.toList());
        List<PatientAuthorizationEntity> patientAuthorizations = patientAuthorizationRepository.findByPatient_Id(patientId).get();
        return StreamSupport.stream(patientSessionRepository.findAllById(sessions).spliterator(), false)
                .map(patientSessionEntity ->
                        SubmissionSession.builder()
                                .sessionId(patientSessionEntity.getId())
                                .dateOfService(patientSessionEntity.getServiceDate())
                                .authorizationSession(patientSessionEntity.getPatientAuthorization() != null ? AuthorizationMapper.map(patientSessionEntity.getPatientAuthorization()) : null)
                                .insuranceCompanyId(insuranceCompany)
                                .patientAuthorizations(AuthorizationMapper.map(patientAuthorizations))
                                .build()).collect(Collectors.toList());
    }

    private PatientSession pickPatientSession(Long sessionId) {
        Optional<PatientSession> patientSession = invoicesSessionServiceLines.stream()
                .filter(serviceLine -> serviceLine.getSessionId().getId().equals(sessionId))
                .map(serviceLine -> {
                    return serviceLine.getSessionId();
                }).findFirst();
        return patientSession.isPresent() ? patientSession.get() : null;
    }

    public void updateAuthorization(Map<Long , Integer> authorizationToUpdate){
        for (var entry : authorizationToUpdate.entrySet()) {
            PatientAuthorizationEntity patientAuthorizationEntity = patientAuthorizationRepository.findById(entry.getKey()).get();
            patientAuthorizationEntity.setRemaining(entry.getValue());
            patientAuthorizationRepository.save(patientAuthorizationEntity);
        }
    }

}
