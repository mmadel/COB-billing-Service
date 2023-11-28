package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PatientSessionRepository extends PagingAndSortingRepository<PatientSessionEntity,Long> {

    @Query(value = "select ps from PatientSessionEntity ps where " +
            "JSON_EXTRACT(ps.patientInfo, '$.patientId')= :patientId ")
    Page<PatientSessionEntity> find(Pageable paging ,@Param("patientId") Long patientId);
}
