package com.cob.billing.repositories.bill.posting;

import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientSessionServiceLinePaymentInfoRepository extends CrudRepository<PatientSessionServiceLinePaymentInfoEntity, Long> {
}
