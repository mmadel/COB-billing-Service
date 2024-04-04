package com.cob.billing.usecases.bill.posting;


import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateSessionStatusUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public void update(List<SessionServiceLinePayment> sessionServiceLinePayments) {
        List<PatientSessionEntity> submitSessions = new ArrayList<>();
        List<PatientSessionEntity> partialSessions = new ArrayList<>();
        List<Long> serviceLinesIds = sessionServiceLinePayments.stream().map(serviceLinePayment -> serviceLinePayment.getServiceLineId())
                .collect(Collectors.toList());
        List<PatientSessionEntity> sessions = patientSessionRepository.findSessionByServiceCodeIds(serviceLinesIds);
        sessions.stream()
                .forEach(patientSessionEntity -> {
                    if (patientSessionEntity.getServiceCodes().stream().allMatch(patientSessionServiceLineEntity -> patientSessionServiceLineEntity.getType().equals("Close"))) {
                        patientSessionEntity.setStatus(PatientSessionStatus.Submit);
                        submitSessions.add(patientSessionEntity);
                    } else {
                        patientSessionEntity.setStatus(PatientSessionStatus.Partial);
                        partialSessions.add(patientSessionEntity);
                    }
                });
        if (submitSessions.size() > 0)
            patientSessionRepository.saveAll(submitSessions);
        if (partialSessions.size() > 0)
            patientSessionRepository.saveAll(partialSessions);

    }
}
