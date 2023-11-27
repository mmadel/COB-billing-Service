package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientSessionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ModelMapper mapper;

    public Long create(PatientSession model) {
        PatientSessionEntity toBeCreated = mapper.map(model, PatientSessionEntity.class);
        return patientSessionRepository.save(toBeCreated).getId();
    }
}
