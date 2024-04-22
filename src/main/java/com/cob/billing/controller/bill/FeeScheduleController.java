package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleModel;
import com.cob.billing.usecases.bill.tools.fee.schedule.*;
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
    @Autowired
    FindFeeSchedulesLinesUseCase findFeeSchedulesLinesUseCase;
    @Autowired
    RemoveFeeSchedulesUseCase removeFeeSchedulesUseCase;
    @Autowired
    FindDefaultFeeScheduleByCPTCodeUseCase findDefaultFeeScheduleByCPTCodeUseCase;
    @Autowired
    FindFeeScheduleMetaDataUseCase findFeeScheduleMetaData;


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
    @GetMapping("/find/cpt/{cptCode}")
    public ResponseEntity findByCPTCode(@PathVariable String cptCode){
        return new ResponseEntity(findDefaultFeeScheduleByCPTCodeUseCase.find(cptCode), HttpStatus.OK);

    }
    @GetMapping("/find/lines/fee/{id}")
    public ResponseEntity findLines(@PathVariable Long id) {
        return new ResponseEntity(findFeeSchedulesLinesUseCase.findLines(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(removeFeeSchedulesUseCase.remove(id), HttpStatus.OK);
    }

    @GetMapping("/find/meta-data")
    public ResponseEntity findMetaData(){
        return new ResponseEntity(findFeeScheduleMetaData.find(), HttpStatus.OK);
    }

}
