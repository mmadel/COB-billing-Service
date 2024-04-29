package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindSubmittedSessionsByPatientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posting")
public class InsuranceCompanyBatchController {
    @Autowired
    FindSubmittedSessionsByPatientUseCase findSubmittedSessionsByPatientUseCase;


    @GetMapping("/find/patient/{patientId}")
    public ResponseEntity<Object> findClient(@RequestParam(name = "offset") int offset,
                                             @RequestParam(name = "limit") int limit
            , @PathVariable(name = "patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status submitted",
                        HttpStatus.OK, null, findSubmittedSessionsByPatientUseCase.find(offset + 1, limit, patientId));
    }

    @PostMapping("/find")
    public ResponseEntity<Object> findClientFiltered(@RequestParam(name = "offset") int offset,
                                                     @RequestParam(name = "limit") int limit
            , @RequestBody PostingSearchCriteria postingSearchCriteria) {
        return ResponseHandler
                .generateResponse("Successfully finding  filtered patients with session status submitted",
                        HttpStatus.OK, null, findSubmittedSessionsByPatientUseCase.find(offset + 1, limit, postingSearchCriteria));
    }

    @GetMapping("/find/insurance/company/{insuranceCompanyId}")
    public ResponseEntity<Object> findInsuranceCompany(@PathVariable Long insuranceCompanyId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients by insurance company with session status prepared",
                        HttpStatus.OK, findSubmittedSessionsByPatientUseCase.findInsuranceCompany(insuranceCompanyId));
    }

}
