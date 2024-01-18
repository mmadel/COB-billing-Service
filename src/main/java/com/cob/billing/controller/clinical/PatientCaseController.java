package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.cases.CreatePatientCaseUseCase;
import com.cob.billing.usecases.clinical.patient.cases.DeletePatientCaseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/case")
public class PatientCaseController {
    @Autowired
    CreatePatientCaseUseCase createPatientCaseUseCase;
    @Autowired
    DeletePatientCaseUseCase deletePatientCaseUseCase;

    @PostMapping("/create/patient/{patientId}")
    public ResponseEntity<Object> create(@RequestBody PatientCase model, @PathVariable Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Case",
                        HttpStatus.OK, createPatientCaseUseCase.create(model, patientId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Case",
                        HttpStatus.OK,
                        deletePatientCaseUseCase.delete(id));
    }
}
