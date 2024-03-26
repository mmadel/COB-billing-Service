package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.fee.schedule.FeeScheduleLineEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.model.response.ClinicResponse;
import com.cob.billing.model.response.FeeScheduleLineResponse;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindFeeScheduleLinesUseCase {
    @Autowired
    FeeScheduleLineRepository feeScheduleLineRepository;
    @Autowired
    ModelMapper mapper;

    public FeeScheduleLineResponse find(Pageable paging, Long feeId) {
        Page<FeeScheduleLineEntity> pages  = feeScheduleLineRepository.findByFeeSchedule_Id(paging, feeId);
        long total = (pages).getTotalElements();
        List<FeeScheduleLineModel> records = pages.stream().map(feeScheduleLineEntity -> mapper.map(feeScheduleLineEntity, FeeScheduleLineModel.class))
                .collect(Collectors.toList());
        return FeeScheduleLineResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
