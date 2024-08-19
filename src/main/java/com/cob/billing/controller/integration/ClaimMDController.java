package com.cob.billing.controller.integration;

import com.cob.billing.usecases.integration.claim.md.GetClaimsHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ch")
public class ClaimMDController {

    @Autowired
    GetClaimsHistoryUseCase getClaimsHistoryUseCase;

    @GetMapping("/get/responseId/{responseId}")
    public ResponseEntity get(@PathVariable Long responseId) {
        return new ResponseEntity(getClaimsHistoryUseCase.get(responseId), HttpStatus.OK);
    }
}
