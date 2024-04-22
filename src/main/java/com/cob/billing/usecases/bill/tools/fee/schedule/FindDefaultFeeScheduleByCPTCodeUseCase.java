package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindDefaultFeeScheduleByCPTCodeUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    ModelMapper mapper;

    @Cacheable("Fee-schedule")
    public FeeScheduleLineModel find(String cpt) {
        System.out.println("Find Fee-schedule");
        FeeScheduleEntity feeSchedule = feeScheduleRepository.findDefaultFee().get();
        Optional<FeeScheduleLineModel> lineModelOptional = feeSchedule.getFeeLines()
                .stream()
                .filter(feeScheduleLineModel -> feeScheduleLineModel.getCptCode().equals(cpt))
                .findFirst();
        if (lineModelOptional.isPresent())
            return lineModelOptional.get();
        else
            return new FeeScheduleLineModel();
    }
}
