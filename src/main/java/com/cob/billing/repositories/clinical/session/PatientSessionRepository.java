package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientSessionRepository extends PagingAndSortingRepository<PatientSessionEntity,Long> {
}
