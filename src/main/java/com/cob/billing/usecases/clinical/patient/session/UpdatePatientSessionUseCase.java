package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import com.cob.billing.util.ListUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class UpdatePatientSessionUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    PatientRepository patientRepository;

    @Transactional
    public Long update(PatientSession model) {
        PatientEntity patient = patientRepository.findById(model.getPatientId()).get();
        PatientSessionEntity toBeUpdated = mapper.map(model, PatientSessionEntity.class);
        toBeUpdated.setPatient(patient);
        boolean allInvoice = model.getServiceCodes().stream()
                .allMatch(obj -> "Invoice".equals(obj.getType()));
        if (!allInvoice)
            toBeUpdated.setStatus(changeSessionStatus(model.getId(), model.getServiceCodes()));
        removeServiceCodes(model.getId(), model.getServiceCodes());
        setDefaultCorrectClaimFlag(toBeUpdated);
        boolean correctSession = toBeUpdated.getServiceCodes().stream()
                .anyMatch(obj -> obj.getIsCorrect());
        if (correctSession)
            markNewServiceLineAsCorrect(toBeUpdated);
        return patientSessionRepository.save(toBeUpdated).getId();
    }


    private void addServiceCodes(PatientSessionEntity entity, List<ServiceLine> serviceCodes) {
        List<PatientSessionServiceLineEntity> newServiceLines = serviceCodes.stream()
                .filter(serviceLine -> serviceLine.getId() == null)
                .map(serviceLine -> mapper.map(serviceLine, PatientSessionServiceLineEntity.class))
                .collect(Collectors.toList());
        newServiceLines.forEach(serviceLineEntity -> {
            entity.addServiceCode(serviceLineEntity);
        });
    }

    private void removeServiceCodes(Long entityId, List<ServiceLine> serviceLines) {
        PatientSessionEntity originalEntity = patientSessionRepository.findById(entityId).get();
        List<Long> originalIds = originalEntity.getServiceCodes()
                .stream()
                .map(PatientSessionServiceLineEntity::getId).collect(Collectors.toList());
        List<Long> submittedIds = serviceLines
                .stream()
                .map(ServiceLine::getId).collect(Collectors.toList());
        List<Long> idsToBeRemoved = ListUtils.returnDifference(originalIds, submittedIds);
        if (idsToBeRemoved.size() > 0)
            serviceLineRepository.deleteAllById(idsToBeRemoved);
    }

    private PatientSessionStatus changeSessionStatus(Long sessionId, List<ServiceLine> serviceCodes) {
        PatientSessionStatus patientSessionStatus = PatientSessionStatus.Prepare;
        PatientSessionEntity patientSessionEntity = patientSessionRepository.findById(sessionId).get();
        if (patientSessionEntity != null) {
            boolean isSessionHAseNewServiceLine = checkSessionHasNewServiceLine(serviceCodes);
            for (int i = 0; i < patientSessionEntity.getServiceCodes().size(); i++) {
                PatientSessionServiceLineEntity serviceLine = patientSessionEntity.getServiceCodes().get(i);
                if (isSessionHAseNewServiceLine
                        && (serviceLine.getType().equals("Invoice") || serviceLine.getType().equals("Close"))) {
                    patientSessionStatus = PatientSessionStatus.Partial;
                    break;
                }
            }
        }
        return patientSessionStatus;
    }

    private Boolean checkSessionHasNewServiceLine(List<ServiceLine> serviceCodes) {
        AtomicReference<Boolean> hasNewServiceLine = new AtomicReference<>(false);
        for (int i = 0; i < serviceCodes.size(); i++) {
            ServiceLine serviceLine = serviceCodes.get(i);
            if (serviceLine.getId() == null && serviceLine.getType().equals("Initial")) {
                hasNewServiceLine.set(true);
                break;
            }
        }
        return hasNewServiceLine.get();
    }

    private void setDefaultCorrectClaimFlag(PatientSessionEntity patientSession) {
        patientSession.getServiceCodes().forEach(patientSessionServiceLineEntity -> {
            if (patientSessionServiceLineEntity.getIsCorrect() == null)
                patientSessionServiceLineEntity.setIsCorrect(false);
        });
    }

    private void markNewServiceLineAsCorrect(PatientSessionEntity patientSession) {
        patientSession.getServiceCodes().stream()
                .filter(patientSessionServiceLineEntity -> patientSessionServiceLineEntity.getId() == null)
                .forEach(patientSessionServiceLineEntity -> {
                    patientSessionServiceLineEntity.setIsCorrect(true);
                });
    }
}
