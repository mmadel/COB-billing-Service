package com.cob.billing.repositories.bill.balance;

import com.cob.billing.entity.bill.balance.PatientBalanceSettingsEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientBalanceSettingsRepository extends CrudRepository<PatientBalanceSettingsEntity, Long> {
}
