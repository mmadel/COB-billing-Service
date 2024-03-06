package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.model.response.PatientSessionResponse;
import com.cob.billing.model.response.ReferringProviderResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindSessionByPatientUseCase {

    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ModelMapper mapper;

    public PatientSessionResponse find(Pageable paging, Long patientId) {
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(
                        destination.getPatientName());
            }
        });
        Page<PatientSessionEntity> pages = patientSessionRepository.findByPatient_Id(paging, patientId);
        long total = (pages).getTotalElements();
        List<PatientSession> records = pages.stream().map(patientSessionEntity -> {
                    PatientSession patientSession = mapper.map(patientSessionEntity, PatientSession.class);
                    patientSession.setPatientName(patientSessionEntity.getPatient().getLastName() + ',' + patientSessionEntity.getPatient().getFirstName());
                    patientSession.setPatientId(patientSessionEntity.getPatient().getId());
                    return patientSession;
                })
                .collect(Collectors.toList());
        records.forEach(patientSession -> {
            boolean isAnyServiceLineCorrect = patientSession.getServiceCodes().stream()
                    .anyMatch(obj -> obj.getIsCorrect() != null && obj.getIsCorrect());
            patientSession.setIsCorrectSession(isAnyServiceLineCorrect);
        });
        return PatientSessionResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
