package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleLineRepository;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFeeScheduleUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    FeeScheduleLineRepository feeScheduleLineRepository;
    @Autowired
    ModelMapper mapper;
    public Long createFeeSchedule(FeeScheduleModel model) {
        FeeScheduleEntity feeSchedule = mapper.map(model , FeeScheduleEntity.class);
        FeeScheduleEntity created = feeScheduleRepository.save(feeSchedule);
        feeSchedule.getFeeLines().forEach(feeScheduleLineEntity -> feeScheduleLineEntity.setFeeSchedule(created));
        feeScheduleLineRepository.saveAll(feeSchedule.getFeeLines());
        return feeSchedule.getId();
    }
}
