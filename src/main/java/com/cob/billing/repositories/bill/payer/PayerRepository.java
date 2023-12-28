package com.cob.billing.repositories.bill.payer;

import com.cob.billing.entity.bill.payer.PayerEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayerRepository extends JpaRepository<PayerEntity, Long> {

    @Query(value = "select pe from PayerEntity pe where pe.payerId in :payerIds")
    List<PayerEntity> findByListAssignedPayers(@Param("payerIds") List<Long> payerIds);

    @Query("SELECT pe FROM PayerEntity pe where " +
            "pe.name LIKE %:name%")
    List<PayerEntity> findByName(@Param("name") String name);

    @Query("SELECT pe FROM PayerEntity pe WHERE pe.payerId not in :payerIds")
    List<PayerEntity> findByListNotAssignedPayers(@Param("payerIds") List<Long> payerIds);
}
