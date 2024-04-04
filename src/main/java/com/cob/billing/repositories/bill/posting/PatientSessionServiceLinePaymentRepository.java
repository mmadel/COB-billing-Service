package com.cob.billing.repositories.bill.posting;

import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentEntity;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientSessionServiceLinePaymentRepository extends CrudRepository<PatientSessionServiceLinePaymentEntity,Long> {

    @Query("select  new com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment(slp.balance,slp.payment,slp.adjust,slp.serviceLine.id ,  slp.createdAt) from PatientSessionServiceLinePaymentEntity slp where slp.serviceLine.id IN :serviceLinesIds")
    Optional<List<SessionServiceLinePayment>> findByServiceLines(@Param("serviceLinesIds") List<Long> serviceLinesIds);
}
