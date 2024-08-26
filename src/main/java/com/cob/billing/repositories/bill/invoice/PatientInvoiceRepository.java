package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface PatientInvoiceRepository extends PagingAndSortingRepository<PatientInvoiceEntity, Long> {
    Optional<PatientInvoiceEntity> findBySubmissionId(Long submissionId);

    @Query("SELECT DISTINCT pi FROM PatientInvoiceEntity pi INNER JOIN FETCH  pi.invoiceDetails pid " +
            "WHERE (:dosStart is null or pid.patientSession.serviceDate >= :dosStart) " +
            "AND (:dosEnd is null or pid.patientSession.serviceDate <= :dosEnd) " +
            "AND ((:client is null or upper(pi.patient.firstName) LIKE CONCAT('%',:client,'%')) OR (:client is null or upper(pi.patient.lastName) LIKE CONCAT('%',:client,'%'))) " +
            "AND ((:provider is null or upper(FUNCTION('jsonb_extract_path_text',pid.patientSession.doctorInfo, '$.doctorFirstName')) LIKE CONCAT('%',:provider,'%') " +
            "OR (:provider is null or upper(FUNCTION('jsonb_extract_path_text',pid.patientSession.doctorInfo, '$.doctorLastName')) LIKE CONCAT('%',:provider,'%'))))" +
            "AND (:submitStart is null or pi.createdAt >= :submitStart) " +
            "AND (:submitEnd is null or pi.createdAt <= :submitEnd)  " +
            "AND (:insuranceCompany is null or upper(FUNCTION('jsonb_extract_path_text',pi.insuranceCompany, '$.name')) LIKE CONCAT('%',:insuranceCompany,'%')) ")
    List<PatientInvoiceEntity> search(@Param("insuranceCompany") String insuranceCompany
            , @Param("client") String client
            , @Param("provider") String provider
            , @Param("dosStart") Long dosStart
            , @Param("dosEnd") Long dosEnd
            , @Param("submitStart") Long submitStart
            , @Param("submitEnd") Long submitEnd);

    @Query("SELECT  pi from PatientInvoiceEntity pi " +
            "WHERE pi.id IN (SELECT pid.patientInvoice.id FROM PatientInvoiceDetailsEntity pid " +
            "JOIN pid.serviceLine pssl " +
            "WHERE pssl.id IN :serviceLines)")
    Optional<PatientInvoiceEntity> findByServiceLines(@Param("serviceLines") List<Long> serviceLines);
}
