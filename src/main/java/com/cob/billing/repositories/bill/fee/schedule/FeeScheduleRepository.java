package com.cob.billing.repositories.bill.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FeeScheduleRepository extends CrudRepository<FeeScheduleEntity,Long> {
    @Query("Select fs from FeeScheduleEntity fs where fs.defaultFee = true")
    Optional<FeeScheduleEntity> findDefaultFee();
}
