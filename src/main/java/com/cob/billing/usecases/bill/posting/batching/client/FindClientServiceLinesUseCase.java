package com.cob.billing.usecases.bill.posting.batching.client;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.model.response.SessionServiceLinePaymentResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.BuildSessionServiceLinePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindClientServiceLinesUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    BuildSessionServiceLinePayment sessionServiceLinePayment;

    public SessionServiceLinePaymentResponse find(int offset, int limit, PostingSearchCriteria postingSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findServiceLines(postingSearchCriteria.getEntityId(), postingSearchCriteria.getStartDate(), postingSearchCriteria.getEndDate());
        return sessionServiceLinePayment.build(patientSessionEntities, offset, limit);
    }
    public SessionServiceLinePaymentResponse find(int offset, int limit, Long patientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findServiceLines(patientId,null,null);
        return sessionServiceLinePayment.build(patientSessionEntities, offset, limit);
    }
}
