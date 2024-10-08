package com.cob.billing.usecases.clinical.patient.session.update;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import com.cob.billing.util.ListUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class UpdatePatientSessionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;

    @Transactional
        public Long update(PatientSession model) {
        PatientEntity patient = patientRepository.findById(model.getPatientId()).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(destination.getStatus());}
        });
        PatientSessionEntity toBeUpdated = mapper.map(model, PatientSessionEntity.class);
        toBeUpdated.setStatus(model.getStatus());
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
//        List<PatientSubmittedClaim> submittedSessions = getUpdatedServiceLinesSubmitted(model);
//        if (submittedSessions.size() != 0)
//            eventPublisher.publishEvent(new SessionEvent(this, model, submittedSessions));
        return patientSessionRepository.save(toBeUpdated).getId();
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

    private List<PatientSubmittedClaim> getUpdatedServiceLinesSubmitted(PatientSession patientSession) {
        List<Long> serviceLinesIds = patientSession.getServiceCodes().stream()
                .filter(serviceLine -> serviceLine.getIsChanged() != null && serviceLine.getIsChanged())
                .map(serviceLine -> serviceLine.getId()).collect(Collectors.toList());
        return patientSubmittedClaimRepository.findByServiceLines(serviceLinesIds);
    }
}
