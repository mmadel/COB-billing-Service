package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindFeeSchedulesLinesUseCase {
    @Autowired
    FeeScheduleRepository feeScheduleRepository;
    public List<FeeScheduleLineModel> findLines(Long FeeScheduleId ){
        return feeScheduleRepository.findById(FeeScheduleId ).get().getFeeLines();
    }
}
