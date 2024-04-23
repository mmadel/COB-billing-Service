package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindDefaultFeeScheduleUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    ModelMapper mapper;
    public FeeScheduleModel find(){
        return mapper.map(feeScheduleRepository.findDefaultFee().get(), FeeScheduleModel.class);
    }
}
