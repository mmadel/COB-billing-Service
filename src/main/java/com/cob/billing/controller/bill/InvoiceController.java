package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.invoice.InvoiceRequestCreation;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.CreateElectronicInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedPatientSessionUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    FindNotSubmittedPatientSessionUseCase findNotSubmittedPatientSessionUseCase;
    @Autowired
    CreateInvoiceUseCase createInvoiceUseCase;
    @Autowired
    CreateElectronicInvoiceUseCase createElectronicInvoiceUseCase;

    @GetMapping("/find/clients")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status prepared",
                        HttpStatus.OK, null, findNotSubmittedPatientSessionUseCase.findNotSubmittedSession(paging));
    }

    @GetMapping("/find/clientId/{clientId}")
    public ResponseEntity find(@PathVariable Long clientId) {
        return new ResponseEntity<>(findNotSubmittedPatientSessionUseCase.findNotSubmittedSessionByPatient(clientId), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public void create(@RequestBody InvoiceRequest invoiceRequest,
                       HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline");
        createInvoiceUseCase.create(invoiceRequest, response);
    }

    @PostMapping("/create/electronic")
    public ResponseEntity<Object> createElectronic(@RequestBody InvoiceRequest invoiceRequest,
                                 HttpServletResponse response) throws JsonProcessingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "inline");
        Gson gson = new Gson();
        String toJson = gson.toJson(createElectronicInvoiceUseCase.create(invoiceRequest));
        return new ResponseEntity<>(toJson , HttpStatus.OK);
    }
}
