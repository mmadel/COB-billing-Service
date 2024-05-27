package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.usecases.bill.posting.ConstructServiceLinesPaymentsUseCase;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/session/payment")
@PreAuthorize("hasAnyRole('patient-role','balance-statement-payment-role')")
public class EnterPaymentController {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;
    @Autowired
    ConstructServiceLinesPaymentsUseCase constructServiceLinesPaymentsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/{serviceLinesIds}")
    public ResponseEntity find(@PathVariable Long[] serviceLinesIds) {
        return new ResponseEntity(constructServiceLinesPaymentsUseCase.construct(Arrays.asList(serviceLinesIds)), HttpStatus.OK);
    }
}
