package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface PatientInvoiceRepository extends PagingAndSortingRepository<PatientInvoiceEntity, Long> {
    Optional<PatientInvoiceEntity> findBySubmissionId(Long submissionId);

    List<PatientInvoiceEntity> search(@Param("insuranceCompany") String insuranceCompany
            , @Param("client") String client
            , @Param("provider") String provider
            , @Param("dosStart") Long dosStart
            , @Param("dosEnd") Long dosEnd
            , @Param("submitStart") Long submitStart
            , @Param("submitEnd") Long submitEnd);

}
