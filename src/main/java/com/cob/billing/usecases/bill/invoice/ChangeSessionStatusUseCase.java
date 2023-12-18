package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeSessionStatusUseCase {
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public void change(Long serviceLineId) {
        PatientSessionServiceLineEntity serviceLine = serviceLineRepository.findById(serviceLineId).get();
        serviceLine.setType("Invoice");
        serviceLineRepository.save(serviceLine);
        PatientSessionEntity patientSessionEntity = patientSessionRepository.findSessionByServiceCodeId(serviceLine.getId());
        changeSessionStatusToPartial(patientSessionEntity);
        changeSessionStatusToSubmit(patientSessionEntity);
    }

    private void changeSessionStatusToPartial(PatientSessionEntity patientSessionEntity) {
        PatientSessionStatus patientSessionStatus = patientSessionEntity.getStatus();
        if (patientSessionStatus.equals(PatientSessionStatus.Prepare)){
            patientSessionEntity.setStatus(PatientSessionStatus.Partial);
            patientSessionRepository.save(patientSessionEntity);
        }
    }
    private void changeSessionStatusToSubmit(PatientSessionEntity patientSessionEntity) {
        boolean isSessionNotToSubmit = patientSessionEntity.getServiceCodes().stream()
                .anyMatch(serviceLine -> serviceLine.getType().equals("Initial"));
        if (!isSessionNotToSubmit) {
            patientSessionEntity.setStatus(PatientSessionStatus.Submit);
            patientSessionRepository.save(patientSessionEntity);
        }
    }
}
