package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateFeeScheduleUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    ModelMapper mapper;

    public Long createFeeSchedule(FeeScheduleModel model) {
        feeScheduleRepository.findAll().spliterator();
        List<FeeScheduleEntity> feeScheduleEntities =
                StreamSupport.stream(feeScheduleRepository.findAll().spliterator(), false)
                        .filter(feeScheduleEntity -> feeScheduleEntity.getDefaultFee())
                        .collect(Collectors.toList());
        FeeScheduleEntity feeSchedule = mapper.map(model, FeeScheduleEntity.class);
        if(feeScheduleEntities.isEmpty())
            feeSchedule.setDefaultFee(true);
        else
            feeSchedule.setDefaultFee(false);
        FeeScheduleEntity created = feeScheduleRepository.save(feeSchedule);
        return created.getId();
    }
}
