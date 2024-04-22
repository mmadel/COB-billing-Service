package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RemoveFeeSchedulesUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;


    @Transactional
    @CacheEvict(value="Fee-schedule", allEntries=true)
    public Long remove(Long id) {
        FeeScheduleEntity feeSchedule = feeScheduleRepository.findById(id).get();
        feeScheduleRepository.delete(feeSchedule);
        return id;
    }
}
