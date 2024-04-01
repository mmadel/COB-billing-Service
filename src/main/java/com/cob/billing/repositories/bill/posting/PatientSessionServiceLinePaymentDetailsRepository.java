package com.cob.billing.repositories.bill.posting;

import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentDetailsEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientSessionServiceLinePaymentDetailsRepository extends CrudRepository<PatientSessionServiceLinePaymentDetailsEntity, Long> {
}
