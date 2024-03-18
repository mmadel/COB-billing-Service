package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientAuthorizationRepository extends PagingAndSortingRepository<PatientAuthorizationEntity, Long> {
    Optional<List<PatientAuthorizationEntity>> findByPatient_Id(Long patientId);
}
