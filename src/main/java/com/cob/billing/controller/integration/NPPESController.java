package com.cob.billing.controller.integration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/nppes")

public class NPPESController {

    @GetMapping("/find/provider/npi/{npi}")
    public ResponseEntity findProviderByNPI(@PathVariable Long npi){
        return new ResponseEntity(HttpStatus.OK);
    }
}
