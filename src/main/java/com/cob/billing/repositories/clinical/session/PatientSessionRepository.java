package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.enums.PatientSessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientSessionRepository extends PagingAndSortingRepository<PatientSessionEntity, Long> {

    Page<PatientSessionEntity> findByPatient_Id(Pageable pageable, Long patientID);

    @Query("SELECT  ps FROM PatientSessionEntity ps  JOIN ps.serviceCodes serviceCode WHERE serviceCode.id =:serviceCodeId")
    PatientSessionEntity findSessionByServiceCodeId(@Param("serviceCodeId") Long serviceCodeId);

    @Query("SELECT DISTINCT s , sc FROM PatientSessionEntity s INNER JOIN FETCH s.serviceCodes sc " +
            "WHERE (s.status = 'Prepare' OR s.status = 'Partial')" +
            "AND sc.type NOT IN ('Invoice', 'Cancel','Close')")
    List<PatientSessionEntity> findPrepareAndPartialSessions();

    @Query("SELECT DISTINCT s FROM PatientSessionEntity s INNER JOIN FETCH s.serviceCodes sc " +
            "WHERE (s.status = 'Prepare' OR s.status = 'Partial')" +
            "AND sc.type NOT IN ('Invoice', 'Cancel','Close')" +
            "AND s.patient.id= :patientId")
    List<PatientSessionEntity> findPrepareAndPartialSessionsByPatient(@Param("patientId") Long patientId);

    @Query("SELECT DISTINCT s FROM PatientSessionEntity s INNER JOIN FETCH s.serviceCodes sc " +
            "WHERE (s.status = 'Prepare' OR s.status = 'Partial')" +
            "AND sc.type NOT IN ('Invoice', 'Cancel','Close')" +
            "AND s.patient.id= :patientId " +
            "AND (:dateFrom is null or s.serviceDate >= :dateFrom) " +
            "AND (:dateTo is null or s.serviceDate <= :dateTo)" +
            "AND (:caseTitle is null or upper(s.caseTitle) LIKE CONCAT('%',:caseTitle,'%'))" +
            "AND ((:provider is null or upper(JSON_EXTRACT(s.doctorInfo, '$.doctorFirstName')) LIKE CONCAT('%',:provider,'%')) " +
            "OR ((:provider is null or upper(JSON_EXTRACT(s.doctorInfo, '$.doctorLastName')) LIKE CONCAT('%',:provider,'%'))) ) ")
    List<PatientSessionEntity> findPrepareAndPartialSessionsByPatientFiltered(@Param("patientId") Long patientId, @Param("dateFrom") Long dateFrom
            , @Param("dateTo") Long dateTo
            , @Param("provider") String provider
            , @Param("caseTitle") String caseTitle);


    @Query("SELECT  DISTINCT s FROM PatientSessionEntity s INNER JOIN  s.serviceCodes sc " +
            "WHERE (s.status = 'Submit' OR s.status = 'Partial')" +
            "AND s.patient.id= :patientId " +
            "AND sc.type  IN ('Invoice') ")
    List<PatientSessionEntity> findSubmittedSessionsByPatient(@Param("patientId") Long patientId);

    @Query("SELECT  DISTINCT s FROM PatientSessionEntity s INNER JOIN FETCH  s.serviceCodes sc " +
            "WHERE (s.status = 'Submit' OR s.status = 'Partial')" +
            "AND s.patient.id= :patientId " +
            "AND sc.type  IN ('Invoice') " +
            "AND (:dateFrom is null or s.serviceDate >= :dateFrom) " +
            "AND (:dateTo is null or s.serviceDate <= :dateTo)")
    List<PatientSessionEntity> findSubmittedSessionsByPatientFiltered(@Param("patientId") Long patientId, @Param("dateFrom") Long dateFrom
            , @Param("dateTo") Long dateTo);

    Optional<List<PatientSessionEntity>> findByPatient_Id(Long patientId);

    @Modifying
    @Query("update PatientSessionEntity ps set ps.patientAuthorization.id = :authId where ps.id = :sessionId")
    void assignAuthorization(@Param("sessionId") Long sessionId, @Param("authId") Long authId);
}
