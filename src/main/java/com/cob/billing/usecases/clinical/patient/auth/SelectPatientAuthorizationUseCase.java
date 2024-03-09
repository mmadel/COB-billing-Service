package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class SelectPatientAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void select(Long patientId, Long authorizationId) {
        patientAuthorizationRepository.assignAuthorizationToPatient(patientId, authorizationId);
        List<PatientAuthorizationEntity> filteredList = patientAuthorizationRepository.findByPatient_Id(patientId).get()
                .stream()
                .filter(patientAuthorizationEntity -> patientAuthorizationEntity.getId() != authorizationId)
                .collect(Collectors.toList());
        filteredList.forEach(patientAuthorizationEntity -> {
            patientAuthorizationRepository.unAssignAuthorizationToPatient(patientId, patientAuthorizationEntity.getId());
        });

    }
}
