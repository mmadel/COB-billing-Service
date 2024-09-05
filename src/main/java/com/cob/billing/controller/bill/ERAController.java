package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.era.ERAHistory;
import com.cob.billing.usecases.bill.era.CreateERAHistoryRecordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/era")
public class ERAController {
    @Autowired
    CreateERAHistoryRecordUseCase createERAHistoryRecordUseCase;

    @PostMapping("/create/history")
    public ResponseEntity create(@RequestBody ERAHistory eraHistory) {
        createERAHistoryRecordUseCase.create(eraHistory);
        return new ResponseEntity(HttpStatus.OK);
    }
}
