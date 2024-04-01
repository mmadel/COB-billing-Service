package com.cob.billing.repositories.bill.posting;

import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientSessionServiceLinePaymentRepository extends CrudRepository<PatientSessionServiceLinePaymentEntity,Long> {
}
