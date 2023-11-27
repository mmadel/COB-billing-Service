package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.session.CreatePatientSessionUseCase;
import com.cob.billing.usecases.clinical.patient.session.FindSessionByPatientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/session")
public class SessionController {
    @Autowired
    CreatePatientSessionUseCase createPatientSessionUseCase;
    @Autowired
    FindSessionByPatientUseCase findSessionByPatientUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientSession patientSession) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Session",
                        HttpStatus.OK, createPatientSessionUseCase.create(patientSession));
    }

    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit
            , @PathVariable Long patientId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding Patient Session",
                        HttpStatus.OK, null, findSessionByPatientUseCase.find(paging, patientId));
    }
}
