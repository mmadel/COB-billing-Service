package com.cob.billing.repositories.bill.posting;

import com.cob.billing.entity.bill.payment.PatientPaymentServiceLineEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientPaymentServiceLineRepository extends CrudRepository<PatientPaymentServiceLineEntity,Long> {
}
