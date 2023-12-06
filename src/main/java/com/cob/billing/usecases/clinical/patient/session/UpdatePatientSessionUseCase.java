package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.ServiceLineEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import com.cob.billing.util.ListUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdatePatientSessionUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;

    @Transactional
    public Long update(PatientSession model) {
        PatientSessionEntity toBeUpdated = mapper.map(model, PatientSessionEntity.class);
        removeServiceCodes(model.getId(), model.getServiceCodes());
        return patientSessionRepository.save(toBeUpdated).getId();
    }


    private void addServiceCodes(PatientSessionEntity entity, List<ServiceLine> serviceCodes) {
        List<ServiceLineEntity> newServiceLines = serviceCodes.stream()
                .filter(serviceLine -> serviceLine.getId() == null)
                .map(serviceLine -> mapper.map(serviceLine, ServiceLineEntity.class))
                .collect(Collectors.toList());
        newServiceLines.forEach(serviceLineEntity -> {
            entity.addServiceCode(serviceLineEntity);
        });
    }

    private void removeServiceCodes(Long entityId, List<ServiceLine> serviceLines) {
        PatientSessionEntity originalEntity = patientSessionRepository.findById(entityId).get();
        List<Long> originalIds = originalEntity.getServiceCodes()
                .stream()
                .map(ServiceLineEntity::getId).collect(Collectors.toList());
        List<Long> submittedIds = serviceLines
                .stream()
                .map(ServiceLine::getId).collect(Collectors.toList());
        List<Long> idsToBeRemoved = ListUtils.returnDifference(originalIds, submittedIds);
        if (idsToBeRemoved.size() > 0)
            serviceLineRepository.deleteAllById(idsToBeRemoved);

    }

}
