package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {
}
