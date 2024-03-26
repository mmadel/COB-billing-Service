package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.usecases.bill.tools.fee.schedule.CreateFeeScheduleUseCase;
import com.cob.billing.usecases.bill.tools.fee.schedule.FindFeeSchedulesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fee/schedule")
public class FeeScheduleController {
    @Autowired
    CreateFeeScheduleUseCase createFeeScheduleUseCase;

    @Autowired
    FindFeeSchedulesUseCase findFeeSchedulesUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody FeeScheduleModel model) {
        return new ResponseEntity(createFeeScheduleUseCase.createFeeSchedule(model), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity find() {
        return new ResponseEntity(findFeeSchedulesUseCase.find(), HttpStatus.OK);
    }
}
