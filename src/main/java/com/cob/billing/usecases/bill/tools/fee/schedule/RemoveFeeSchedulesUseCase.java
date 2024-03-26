package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleLineEntity;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleLineRepository;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RemoveFeeSchedulesUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    FeeScheduleLineRepository feeScheduleLineRepository;

    @Transactional
    public Long remove(Long id) {
        List<FeeScheduleLineEntity> lines = feeScheduleLineRepository.findByFeeSchedule_Id(id).get();
        feeScheduleLineRepository.deleteAll(lines);
        feeScheduleRepository.deleteById(id);
        return id;
    }
}
