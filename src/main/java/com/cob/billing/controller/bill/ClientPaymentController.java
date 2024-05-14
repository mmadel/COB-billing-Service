package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.posting.batching.client.FindClientServiceLinesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posting/client")
@PreAuthorize("hasAnyRole('payment-role','batch-client-payment-role')")
public class ClientPaymentController {
    @Autowired
    FindClientServiceLinesUseCase findClientServiceLinesUseCase;

    @GetMapping("/find/patient/{patientId}")
    public ResponseEntity<Object> findClient(@RequestParam(name = "offset") int offset,
                                             @RequestParam(name = "limit") int limit
            , @PathVariable(name = "patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patient session payments",
                        HttpStatus.OK, null, findClientServiceLinesUseCase.find(offset + 1, limit, patientId));
    }

    @PostMapping("/find")
    public ResponseEntity<Object> findClientFiltered(@RequestParam(name = "offset") int offset,
                                                     @RequestParam(name = "limit") int limit
            , @RequestBody PostingSearchCriteria postingSearchCriteria) {
        return ResponseHandler
                .generateResponse("Successfully finding  patient session payments",
                        HttpStatus.OK, null, findClientServiceLinesUseCase.find(offset + 1, limit, postingSearchCriteria));
    }
}
