package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/batch/paymentm")
public class BatchPaymentController {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;

    @PostMapping("/client/create")
    public ResponseEntity create(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/insurance-company/create")
    public ResponseEntity createInsuranceCompany(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }
}
