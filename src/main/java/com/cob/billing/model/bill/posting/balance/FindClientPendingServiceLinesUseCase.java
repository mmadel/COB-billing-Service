package com.cob.billing.model.bill.posting.balance;

import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindClientPendingServiceLinesUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public Long find(Long patientId, PatientSessionSearchCriteria patientSessionSearchCriteria) {
        patientSessionRepository.findClientPendingSessions(patientId
                , patientSessionSearchCriteria.getStartDate()
                , patientSessionSearchCriteria.getEndDate());
        return null;
    }
}
