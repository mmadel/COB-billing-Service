package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.ServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {
    @Query("SELECT distinct pe FROM PatientEntity pe  JOIN pe.sessions session WHERE (session.status = 'Prepare' OR session.status = 'Partial')")
    Page<PatientEntity> findBySessionNotSubmittedByPatient(Pageable paging);

    @Query("SELECT serviceCode  , session FROM PatientEntity pe  JOIN pe.sessions session JOIN session.serviceCodes serviceCode WHERE session.status = 'Submit' AND pe.id =:patientId")
    Page<Object> findBySessionSubmittedByPatient(Pageable paging, @Param("patientId") Long patientId);

    @Query("SELECT distinct pe FROM PatientEntity pe  JOIN pe.sessions session WHERE (session.status = 'Prepare' OR session.status = 'Partial') AND pe.id =:patientId")
    PatientEntity findBySessionStatusByPatient(@Param("patientId") Long patientId);

    @Query("SELECT pe FROM PatientEntity pe where " +
            "pe.firstName LIKE %:name% OR pe.middleName LIKE %:name% OR pe.middleName LIKE %:name%")
    List<PatientEntity> findByName(@Param("name") String name);

}
