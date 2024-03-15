package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.auth.CreatePatientAuthorizationUseCase;
import com.cob.billing.usecases.clinical.patient.auth.DeletePatientAuthorizationUseCase;
import com.cob.billing.usecases.clinical.patient.auth.FetchPatientAuthorizationUseCase;
import com.cob.billing.usecases.clinical.patient.auth.SelectPatientAuthorizationUseCase;
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
    @Autowired
    DeletePatientAuthorizationUseCase deletePatientAuthorizationUseCase;
    @Autowired
    SelectPatientAuthorizationUseCase selectPatientAuthorizationUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientAuthorization model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient-Auth",
                        HttpStatus.OK,
                        createPatientAuthorizationUseCase.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PatientAuthorization model) {
        return ResponseHandler
                .generateResponse("Successfully updated Patient-Auth",
                        HttpStatus.OK,
                        createPatientAuthorizationUseCase.createOrUpdate(model));
    }

    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity<Object> findAll(@PathVariable Long patientId) {
        return new ResponseEntity(fetchPatientAuthorizationUseCase.find(patientId , null), HttpStatus.OK);
    }
    @GetMapping("/find/patientId/{patientId}/session/{sessionId}")
    public ResponseEntity<Object> findBySession(@PathVariable Long patientId, @PathVariable Long sessionId) {
        return new ResponseEntity(fetchPatientAuthorizationUseCase.find(patientId, sessionId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/auth/{authid}")
    public ResponseEntity<Object> delete(@PathVariable Long authid) {
        deletePatientAuthorizationUseCase.delete(authid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/assign/session/{sessionId}/authorization/{authid}")
    public ResponseEntity<Object> selectAuthorization(@PathVariable Long sessionId, @PathVariable Long authid) {
        selectPatientAuthorizationUseCase.select(sessionId, authid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
