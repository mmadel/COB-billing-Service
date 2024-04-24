package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CreateFeeScheduleUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    ModelMapper mapper;

    @CacheEvict(value = "Fee-schedule", allEntries = true)
    public Long createFeeSchedule(FeeScheduleModel model) {
        FeeScheduleEntity feeSchedule = mapper.map(model, FeeScheduleEntity.class);
        if (!feeSchedule.getDefaultFee())
            feeSchedule.setDefaultFee(false);
        FeeScheduleEntity created = feeScheduleRepository.save(feeSchedule);
        return created.getId();
    }
}
