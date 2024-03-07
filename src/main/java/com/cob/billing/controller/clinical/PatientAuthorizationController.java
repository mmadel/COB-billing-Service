package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.auth.CreatePatientAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authorization")
public class PatientAuthorizationController {

    @Autowired
    CreatePatientAuthorizationUseCase createPatientAuthorizationUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientAuthorization model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient-Auth",
                        HttpStatus.OK,
                        createPatientAuthorizationUseCase.create(model));
    }
}
