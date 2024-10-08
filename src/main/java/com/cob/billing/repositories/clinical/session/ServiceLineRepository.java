package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServiceLineRepository extends PagingAndSortingRepository<PatientSessionServiceLineEntity, Long> {

}
