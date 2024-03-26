package com.cob.billing.repositories.bill.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleLineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FeeScheduleLineRepository extends PagingAndSortingRepository<FeeScheduleLineEntity, Long> {

    Optional<List<FeeScheduleLineEntity>> findByFeeSchedule_Id(Long id);

    Page<FeeScheduleLineEntity> findByFeeSchedule_Id(Pageable paging, Long id);
}
