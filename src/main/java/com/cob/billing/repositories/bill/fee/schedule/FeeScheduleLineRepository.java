package com.cob.billing.repositories.bill.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleLineEntity;
import org.springframework.data.repository.CrudRepository;

public interface FeeScheduleLineRepository  extends CrudRepository<FeeScheduleLineEntity,Long> {
}
