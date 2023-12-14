package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.invoice.InvoiceRequestCreation;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceUseCase;
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
    @Autowired
    CreateInvoiceUseCase createInvoiceUseCase;

    @GetMapping("/find/clients")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status prepared",
                        HttpStatus.OK, null, findPatientSessionByStatusUseCase.findNotSubmittedSession(paging));
    }
    @GetMapping("/find/clientId/{clientId}")
    public ResponseEntity find(@PathVariable Long clientId) {
        return new ResponseEntity<>(findPatientSessionByStatusUseCase.findNotSubmittedSessionByPatient(clientId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody InvoiceRequestCreation invoiceRequestCreation) {
        createInvoiceUseCase.create(invoiceRequestCreation);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/create/electronic")
    public ResponseEntity createElectronic() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
