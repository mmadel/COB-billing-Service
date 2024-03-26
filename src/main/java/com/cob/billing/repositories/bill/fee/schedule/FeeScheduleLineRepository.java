package com.cob.billing.repositories.bill.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleLineEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FeeScheduleLineRepository  extends CrudRepository<FeeScheduleLineEntity,Long> {

    Optional<List<FeeScheduleLineEntity>> findByFeeSchedule_Id(Long id);
}
