package com.cob.billing.controller.bill;

import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindSubmittedPatientSessionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posting")
public class PostingController {
    @Autowired
    FindSubmittedPatientSessionUseCase findSubmittedPatientSessionUseCase;

    @GetMapping("/find/client/{clientId}")
    public ResponseEntity<Object> findClient(@PathVariable Long clientId) {
        //Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status prepared",
                        HttpStatus.OK, findSubmittedPatientSessionUseCase.findClient(clientId));
    }
    @GetMapping("/find/insurance/company/{clientId}")
    public ResponseEntity<Object> findInsuranceCompany(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit,
                                       @PathVariable Long clientId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return null;
    }
}
