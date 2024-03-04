package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindSubmittedSessionsByPatientUseCase;
import com.cob.billing.usecases.bill.posting.CreateServiceLinesPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/posting")
public class PostingController {
    @Autowired
    FindSubmittedSessionsByPatientUseCase findSubmittedSessionsByPatientUseCase;
    @Autowired
    CreateServiceLinesPaymentUseCase createServiceLinesPaymentUseCase;

    @GetMapping("/find/patient/{patientId}")
    public ResponseEntity<Object> findClient(@RequestParam(name = "offset") int offset,
                                             @RequestParam(name = "limit") int limit
            , @PathVariable(name = "patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status submitted",
                        HttpStatus.OK, findSubmittedSessionsByPatientUseCase.find(offset, limit, patientId));
    }

    @GetMapping("/find/insurance/company/{insuranceCompanyId}")
    public ResponseEntity<Object> findInsuranceCompany(@PathVariable Long insuranceCompanyId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients by insurance company with session status prepared",
                        HttpStatus.OK, findSubmittedSessionsByPatientUseCase.findInsuranceCompany(insuranceCompanyId));
    }

    @PostMapping("/create/payments/clientId/{clientId}")
    public ResponseEntity create(@RequestBody List<PaymentServiceLine> payments
            , @PathVariable Long clientId) {
        createServiceLinesPaymentUseCase.create(payments, clientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/create/payments/insurance/company")
    public ResponseEntity create(@RequestBody Map<Long, List<PaymentServiceLine>> payments) {
        createServiceLinesPaymentUseCase.create(payments);
        return new ResponseEntity(HttpStatus.OK);
    }
}
