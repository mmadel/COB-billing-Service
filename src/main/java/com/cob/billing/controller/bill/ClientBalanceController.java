package com.cob.billing.controller.bill;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client/balance")
public class ClientBalanceController {

    @GetMapping("/find/awaiting/{serviceLinesIds}")
    public ResponseEntity findAwaiting(@PathVariable Long[] serviceLinesIds) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
    @GetMapping("/find/Finalized/{serviceLinesIds}")
    public ResponseEntity findFinalized(@PathVariable Long[] serviceLinesIds) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
