package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.PatientSessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {
    @Query("SELECT pe FROM PatientEntity pe  JOIN pe.sessions session WHERE session.status = :status")
    Page<PatientEntity> findBySessionStatus(Pageable paging, @Param("status")PatientSessionStatus status);
}
