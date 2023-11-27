package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.session.CreatePatientSessionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session")
public class SessionController {
    @Autowired
    CreatePatientSessionUseCase createPatientSessionUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientSession patientSession) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Session",
                        HttpStatus.OK, createPatientSessionUseCase.create(patientSession));
    }
}
