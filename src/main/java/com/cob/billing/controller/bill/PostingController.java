package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindSubmittedPatientSessionUseCase;
import com.cob.billing.usecases.bill.posting.CreateServiceLinesPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/posting")
public class PostingController {
    @Autowired
    FindSubmittedPatientSessionUseCase findSubmittedPatientSessionUseCase;
    @Autowired
    CreateServiceLinesPaymentUseCase createServiceLinesPaymentUseCase;

    @GetMapping("/find/client/{clientId}")
    public ResponseEntity<Object> findClient(@PathVariable Long clientId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status prepared",
                        HttpStatus.OK, findSubmittedPatientSessionUseCase.findClient(clientId));
    }

    @GetMapping("/find/insurance/company/{insuranceCompanyId}")
    public ResponseEntity<Object> findInsuranceCompany(@PathVariable Long insuranceCompanyId) {
        return ResponseHandler
                .generateResponse("Successfully finding  patients by insurance company with session status prepared",
                        HttpStatus.OK, findSubmittedPatientSessionUseCase.findInsuranceCompany(insuranceCompanyId));
    }

    @PostMapping("/create/payments/clientId/{clientId}")
    public ResponseEntity create(@RequestBody List<PaymentServiceLine> payments
            , @PathVariable Long clientId) {
        createServiceLinesPaymentUseCase.create(payments, clientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/create/payments/insurance/company/{insuranceCompany}")
    public ResponseEntity create(@RequestBody Map<Long, List<PaymentServiceLine>> payments
            , @PathVariable Long insuranceCompany) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
