package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/session/payment")
public class EnterPaymentController {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;
    @Autowired
    FindSessionPaymentUseCase findSessionPaymentUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/find/{serviceLinesIds}")
    public ResponseEntity find(@PathVariable Long[] serviceLinesIds){
        return new ResponseEntity(findSessionPaymentUseCase.findByServiceLines(Arrays.asList(serviceLinesIds)),HttpStatus.OK);
    }
}
