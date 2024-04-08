package com.cob.billing.controller.bill;

import com.cob.billing.usecases.bill.posting.balance.FindClientPendingServiceLinesUseCase;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client/balance")
public class ClientBalanceController {
    @Autowired
    FindClientPendingServiceLinesUseCase findClientPendingServiceLinesUseCase;

    @PostMapping("/find/awaiting/patient/{patientId}")
    public ResponseEntity findAwaiting(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit,
                                       @PathVariable(name = "patientId") Long patientId
            , @RequestBody PatientSessionSearchCriteria patientSessionSearchCriteria) {
        return new ResponseEntity(findClientPendingServiceLinesUseCase.find(offset + 1, limit, patientId, patientSessionSearchCriteria), HttpStatus.OK);
    }

    @PostMapping("/find/Finalized/patient/{patientId}")
    public ResponseEntity findFinalized(@PathVariable(name = "patientId") Long patientId
            , @RequestBody PatientSessionSearchCriteria patientSessionSearchCriteria) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
