package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.response.PatientResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientSessionByStatusUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public PatientResponse findPrepareSessions(Pageable paging) {
        Page<PatientEntity> pages = patientRepository.findBySessionStatus(paging, PatientSessionStatus.Prepare);
        long total = (pages).getTotalElements();
        List<Patient> records = pages.stream()
                .map(patientSessionEntity -> mapper.map(patientSessionEntity, Patient.class))
                .collect(Collectors.toList());

        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
