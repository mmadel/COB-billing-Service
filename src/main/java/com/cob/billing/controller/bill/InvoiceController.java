package com.cob.billing.controller.bill;

import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindPatientSessionByStatusUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    FindPatientSessionByStatusUseCase findPatientSessionByStatusUseCase;

    @GetMapping("/find/prepare")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding  Session with status prepared",
                        HttpStatus.OK, null, findPatientSessionByStatusUseCase.findPrepareSessions(paging));
    }
}
