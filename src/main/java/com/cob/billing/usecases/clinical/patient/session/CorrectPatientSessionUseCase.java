package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorrectPatientSessionUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public PatientSession correct(PatientSession model) {
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(
                        destination.getPatientName());
            }
        });
        // set session status to be PatientSessionStatus.Prepare
        //set all service lines to that session to be Initial
        PatientSessionEntity correctClaim = mapper.map(model, PatientSessionEntity.class);
        correctClaim.setStatus(PatientSessionStatus.Prepare);
        correctClaim.getServiceCodes().stream()
                .forEach(patientSessionServiceLineEntity -> {
                    patientSessionServiceLineEntity.setType("Initial");
                    patientSessionServiceLineEntity.setIsCorrect(true);
                });
        return mapper.map(patientSessionRepository.save(correctClaim), PatientSession.class);

    }
}
