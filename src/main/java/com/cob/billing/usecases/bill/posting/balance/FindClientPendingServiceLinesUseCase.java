package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindClientPendingServiceLinesUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ModelMapper mapper;

    public Long find(Long patientId, PatientSessionSearchCriteria patientSessionSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findClientPendingSessions(patientId
                , patientSessionSearchCriteria.getStartDate()
                , patientSessionSearchCriteria.getEndDate());

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(destination.getPatientName());
            }
        });
        return null;
    }
}
