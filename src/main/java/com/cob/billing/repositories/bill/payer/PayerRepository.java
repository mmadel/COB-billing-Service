package com.cob.billing.repositories.bill.payer;

import com.cob.billing.entity.bill.payer.PayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayerRepository extends JpaRepository<PayerEntity, Long> {
}
