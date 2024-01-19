package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.usecases.bill.payer.AddPayerUseCase;
import com.cob.billing.usecases.bill.payer.FindPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payer")
public class PayerController {
    @Autowired
    FindPayerUseCase findPayerUseCase;
    @Autowired
    AddPayerUseCase addPayerUseCase;

    @GetMapping("/find")
    public ResponseEntity findAll() {
        return new ResponseEntity(findPayerUseCase.find(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Payer model) {
        return new ResponseEntity(addPayerUseCase.add(model), HttpStatus.OK);
    }
}
