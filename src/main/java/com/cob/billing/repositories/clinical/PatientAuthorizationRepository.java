package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientAuthorizationRepository extends PagingAndSortingRepository<PatientAuthorizationEntity, Long> {
}
