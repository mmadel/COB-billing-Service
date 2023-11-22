package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.CreatePatientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    CreatePatientUseCase createPatientUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Patient model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient",
                        HttpStatus.OK,
                        createPatientUseCase.create(model));
    }
}
