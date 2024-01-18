package com.cob.billing.controller.bill;

import com.cob.billing.usecases.bill.payer.FindPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payer")
public class PayerController {
    @Autowired
    FindPayerUseCase findPayerUseCase;
    @GetMapping("/find")
    public ResponseEntity findAll() {
        return new ResponseEntity(findPayerUseCase.find(), HttpStatus.OK);
    }
}
