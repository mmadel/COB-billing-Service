package com.cob.billing.controller.integration;

import com.cob.billing.usecases.integration.nppes.FindProviderByNPIUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/nppes")

public class NPPESController {
    @Autowired
    FindProviderByNPIUseCase findProviderByNPIUseCase;

    @GetMapping("/find/provider/npi/{npi}")
    public ResponseEntity findProviderByNPI(@PathVariable Long npi){
        return new ResponseEntity(findProviderByNPIUseCase.find(npi),HttpStatus.OK);
    }
}
