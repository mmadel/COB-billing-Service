package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.enums.PatientSessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientSessionRepository extends PagingAndSortingRepository<PatientSessionEntity, Long> {

    Page<PatientSessionEntity> findByPatient_Id(Pageable pageable, Long patientID);

    @Query("SELECT  ps FROM PatientSessionEntity ps  JOIN ps.serviceCodes serviceCode WHERE serviceCode.id =:serviceCodeId")
    PatientSessionEntity findSessionByServiceCodeId(@Param("serviceCodeId") Long serviceCodeId);

}
