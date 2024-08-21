package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionClaimResponseEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.model.integration.claimmd.submit.SubmitResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionClaimResponseRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateSessionClaimResponseUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientSessionClaimResponseRepository patientSessionClaimResponseRepository;

    public void create(SubmitResponse submitResponse) throws IllegalArgumentException {
        List<PatientSessionClaimResponseEntity> toBeSaved = new ArrayList<>();
        List<Long> sessionIds = submitResponse.getClaim().stream()
                .map(claimResponse -> Long.parseLong(claimResponse.getRemote_claimid()))
                .collect(Collectors.toList());

        List<PatientSessionEntity> patientSessions =
                StreamSupport.stream(patientSessionRepository.findAllById(sessionIds).spliterator(), false)
                        .collect(Collectors.toList());
        for (ClaimResponse claimResponse : submitResponse.getClaim()) {
            PatientSessionEntity patientSession = patientSessions.stream()
                    .filter(entity -> entity.getId().equals(Long.parseLong(claimResponse.getRemote_claimid())))
                    .findFirst().get();
            PatientSessionClaimResponseEntity sessionClaimResponse = new PatientSessionClaimResponseEntity();
            sessionClaimResponse.setPatientSession(patientSession);
            sessionClaimResponse.setClaimId(Long.valueOf(claimResponse.getClaimmd_id()));
            sessionClaimResponse.setSubmittedFileName(claimResponse.getFilename());
            sessionClaimResponse.setPatientId(claimResponse.getPcn());
            switch (claimResponse.getStatus()) {
                case "R":
                    sessionClaimResponse.setClaimResponseStatus(ClaimResponseStatus.Claim_Rejection);
                    break;
                case "A":
                    sessionClaimResponse.setClaimResponseStatus(ClaimResponseStatus.Claim_Acknowledgment);
                    break;
            }
            sessionClaimResponse.setMessages(claimResponse.getMessages());
            toBeSaved.add(sessionClaimResponse);
        }
        if (!toBeSaved.isEmpty())
            patientSessionClaimResponseRepository.saveAll(toBeSaved);
    }
}
