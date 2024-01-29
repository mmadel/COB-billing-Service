package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindSessionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ModelMapper mapper;

    public PatientSession findById(Long sessionId) {
        PatientSessionEntity entity = patientSessionRepository.findById(sessionId).orElseThrow(() -> new IllegalArgumentException("session not found"));
        return mapper.map(entity, PatientSession.class);

    }
}
