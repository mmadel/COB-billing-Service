package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.clinical.patient.session.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/session")
public class PatientSessionController {
    @Autowired
    CreatePatientSessionUseCase createPatientSessionUseCase;
    @Autowired
    UpdatePatientSessionUseCase updatePatientSessionUseCase;
    @Autowired
    FindSessionByPatientUseCase findSessionByPatientUseCase;
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    FindSessionUseCase findSessionUseCase;
    @Autowired
    CorrectPatientSessionUseCase correctPatientSessionUseCase;
    @Autowired
    UpdatePatientSessionItemUseCase updatePatientSessionItemUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientSession patientSession) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Session",
                        HttpStatus.OK, createPatientSessionUseCase.create(patientSession));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PatientSession patientSession) {
        return ResponseHandler
                .generateResponse("Successfully updated Patient Session",
                        HttpStatus.OK, updatePatientSessionUseCase.update(patientSession));
    }

    @PutMapping("/update/items/serviceLineId/{serviceLineId}")
    public ResponseEntity<Object> updateItems(@PathVariable Long serviceLineId, @RequestBody CPTCode cptCode) {
        return ResponseHandler
                .generateResponse("Successfully updated Patient Session",
                        HttpStatus.OK, updatePatientSessionItemUseCase.update(serviceLineId, cptCode));
    }


    @GetMapping("/find/id/{sessionId}")
    public ResponseEntity<Object> findById(@PathVariable Long sessionId) {
        return ResponseHandler
                .generateResponse("Successfully getting Patient Session",
                        HttpStatus.OK, findSessionUseCase.findById(sessionId));
    }

    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit,
                                       @RequestParam(name = "sort") Optional<String> sort
            , @PathVariable Long patientId) {
        String sortField = "serviceDate";
        String sortType = "desc";
        if (!sort.isEmpty()) {
            String[] parsedSortParam = sort.get().split("%");
            sortType = parsedSortParam[1];
        }
        Pageable paging;
        if (sortType == "desc")
             paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit), Sort.by(sortField).descending());
        else
            paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit), Sort.by(sortField).ascending());
        return ResponseHandler
                .generateResponse("Successfully finding Patient Session",
                        HttpStatus.OK, null, findSessionByPatientUseCase.find(paging, patientId));
    }

    @PutMapping("/correct")
    public ResponseEntity<Object> correctClaim(@RequestBody PatientSession patientSession) {
        return new ResponseEntity<>(correctPatientSessionUseCase.correct(patientSession), HttpStatus.OK);
    }
}
