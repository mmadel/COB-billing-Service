package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConstructServiceLinesPaymentsUseCase {
    @Autowired
    FindSessionPaymentUseCase findSessionPaymentUseCase;
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public List<SessionServiceLinePayment> construct(List<Long> serviceLinesIds) {
        List<SessionServiceLinePayment> sessionServiceLinePayments = findSessionPaymentUseCase.find(serviceLinesIds);


        if (sessionServiceLinePayments.isEmpty()) {
            List<PatientSessionServiceLineEntity> sessionServiceLines = patientSessionRepository.findByServiceLines(serviceLinesIds).get();
            add(sessionServiceLines, sessionServiceLinePayments);
        } else {
            List<Long> serviceLinesIdsWithoutPayments = sessionServiceLinePayments.stream()
                    .filter(id -> sessionServiceLinePayments.stream().noneMatch(slp -> slp.getServiceLineId().equals(id)))
                    .map(SessionServiceLinePayment::getServiceLineId)
                    .collect(Collectors.toList());
            List<PatientSessionServiceLineEntity> sessionServiceLines = patientSessionRepository.findByServiceLines(serviceLinesIdsWithoutPayments).get();
            add(sessionServiceLines, sessionServiceLinePayments);
        }

        return sessionServiceLinePayments;
    }

    private void add(List<PatientSessionServiceLineEntity> sessionServiceLines, List<SessionServiceLinePayment> sessionServiceLinePayments) {
        sessionServiceLines.forEach(patientSessionServiceLineEntity -> {
            SessionServiceLinePayment sessionServiceLinePayment = new SessionServiceLinePayment(patientSessionServiceLineEntity.getCptCode().getCharge()
                    , 0.0, 0.0, patientSessionServiceLineEntity.getId(), null);
            sessionServiceLinePayments.add(sessionServiceLinePayment);
        });
    }
}
