package com.cob.billing.repositories.bill.invoice.tmp;

import com.cob.billing.entity.bill.invoice.tmp.PatientInvoiceRecord;
import com.cob.billing.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientInvoiceRecordRepository extends JpaRepository<PatientInvoiceRecord, Long> {
    Optional<PatientInvoiceRecord> findBySubmissionId(Long submissionId);

    @Query("SELECT DISTINCT pi FROM PatientInvoiceRecord pi INNER JOIN FETCH  pi.claims pc " +
            "WHERE pc.submissionStatus = :status")
    List<PatientInvoiceRecord> findBySubmissionStatus(@Param("status") SubmissionStatus status);

    @Query("SELECT DISTINCT pi FROM PatientInvoiceRecord pi INNER JOIN FETCH  pi.claims pc " +
            "WHERE (:dosStart is null or pc.dateOfService >= :dosStart) " +
            "AND (:dosEnd is null or pc.dateOfService <= :dosEnd) " +
            "AND ((:client is null or upper(pi.patientFirstName) LIKE CONCAT('%',:client,'%')) OR (:client is null or upper(pi.patientLastName) LIKE CONCAT('%',:client,'%'))) " +
            "AND ((:provider is null or upper(pc.providerFirstName) LIKE CONCAT('%',:provider,'%') " +
            "OR (:provider is null or upper(pc.providerLastName) LIKE CONCAT('%',:provider,'%'))))" +
            "AND (:submitStart is null or pi.createdAt >= :submitStart) " +
            "AND (:submitEnd is null or pi.createdAt <= :submitEnd)  " +
            "AND (:insuranceCompany is null or upper(pi.insuranceCompanyName) LIKE CONCAT('%',:insuranceCompany,'%')) " +
            "AND (coalesce(:status)  is null or pc.submissionStatus IN (:status))")
    List<PatientInvoiceRecord> search(@Param("insuranceCompany") String insuranceCompany
            , @Param("client") String client
            , @Param("provider") String provider
            , @Param("dosStart") Long dosStart
            , @Param("dosEnd") Long dosEnd
            , @Param("submitStart") Long submitStart
            , @Param("submitEnd") Long submitEnd
            , @Param("status") List<SubmissionStatus> status);

}
