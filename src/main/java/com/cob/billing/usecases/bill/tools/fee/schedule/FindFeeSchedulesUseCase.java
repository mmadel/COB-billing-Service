package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindFeeSchedulesUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    @Autowired
    ModelMapper mapper;

    public List<FeeScheduleModel> find() {
        return StreamSupport.stream(feeScheduleRepository.findAll().spliterator(), false)
                .map(feeScheduleEntity -> mapper.map(feeScheduleEntity, FeeScheduleModel.class)).collect(Collectors.toList());
    }
    public FeeScheduleModel findById(Long id){
        return mapper.map(feeScheduleRepository.findById(id).get(),FeeScheduleModel.class);
    }
}
