package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.model.response.PatientSessionResponse;
import com.cob.billing.model.response.ReferringProviderResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
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
        Page<PatientSessionEntity> pages = patientSessionRepository.findByPatient_Id(paging, patientId);
        long total = (pages).getTotalElements();
        List<PatientSession> records = pages.stream().map(patientSessionEntity -> mapper.map(patientSessionEntity, PatientSession.class))
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
