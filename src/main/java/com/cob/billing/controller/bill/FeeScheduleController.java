package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.usecases.bill.tools.fee.schedule.CreateFeeScheduleUseCase;
import com.cob.billing.usecases.bill.tools.fee.schedule.FindFeeScheduleLinesUseCase;
import com.cob.billing.usecases.bill.tools.fee.schedule.FindFeeSchedulesUseCase;
import com.cob.billing.usecases.bill.tools.fee.schedule.RemoveFeeSchedulesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    RemoveFeeSchedulesUseCase removeFeeSchedulesUseCase;
    @Autowired
    FindFeeScheduleLinesUseCase findFeeScheduleLinesUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody FeeScheduleModel model) {
        return new ResponseEntity(createFeeScheduleUseCase.createFeeSchedule(model), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity find() {
        return new ResponseEntity(findFeeSchedulesUseCase.find(), HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity find(@PathVariable Long id) {
        return new ResponseEntity(findFeeSchedulesUseCase.findById(id), HttpStatus.OK);
    }
    @GetMapping("/find/lines/fee/{id}")
    public ResponseEntity find(@PathVariable Long id,@RequestParam(name = "offset") int offset,
                               @RequestParam(name = "limit") int limit) {
        Pageable paging = PageRequest.of(offset, limit);
        return new ResponseEntity(findFeeScheduleLinesUseCase.find(paging,id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(removeFeeSchedulesUseCase.remove(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody FeeScheduleModel model) {
        /*todo
            remove logic to remove then update
         */
        return new ResponseEntity(createFeeScheduleUseCase.createFeeSchedule(model), HttpStatus.OK);
    }
}
