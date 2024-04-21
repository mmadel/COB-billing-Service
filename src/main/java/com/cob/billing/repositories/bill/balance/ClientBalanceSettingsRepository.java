package com.cob.billing.repositories.bill.balance;

import com.cob.billing.entity.bill.balance.PatientBalanceSettingsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientBalanceSettingsRepository extends CrudRepository<PatientBalanceSettingsEntity, Long> {
    Optional<PatientBalanceSettingsEntity> findAllByIdIsNotNull();
}
