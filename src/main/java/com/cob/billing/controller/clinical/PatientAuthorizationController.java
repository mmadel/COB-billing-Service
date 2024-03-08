package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.auth.CreatePatientAuthorizationUseCase;
import com.cob.billing.usecases.clinical.patient.auth.FetchPatientAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authorization")
public class PatientAuthorizationController {

    @Autowired
    CreatePatientAuthorizationUseCase createPatientAuthorizationUseCase;
    @Autowired
    FetchPatientAuthorizationUseCase fetchPatientAuthorizationUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientAuthorization model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient-Auth",
                        HttpStatus.OK,
                        createPatientAuthorizationUseCase.create(model));
    }

    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity<Object> findAll(@PathVariable Long patientId) {
        return new ResponseEntity(fetchPatientAuthorizationUseCase.find(patientId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/patientId/{patientId}")
    public ResponseEntity<Object> delete(@PathVariable Long patientId) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
