package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.PatientCase;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.FindPatientCasesUseCase;
import com.cob.billing.usecases.clinical.patient.cases.CreatePatientCaseUseCase;
import com.cob.billing.usecases.clinical.patient.cases.DeletePatientCaseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/case")
@PreAuthorize("hasAnyRole('patient-role')")
public class PatientCaseController {
    @Autowired
    CreatePatientCaseUseCase createPatientCaseUseCase;
    @Autowired
    DeletePatientCaseUseCase deletePatientCaseUseCase;
    @Autowired
    FindPatientCasesUseCase findPatientCasesUseCase;

    @PostMapping("/create/patient/{patientId}")
    public ResponseEntity<Object> create(@RequestBody PatientCase model, @PathVariable Long patientId) {
        return new ResponseEntity<>(createPatientCaseUseCase.create(model, patientId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Case",
                        HttpStatus.OK,
                        deletePatientCaseUseCase.delete(id));
    }

    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity findPatientCases(@PathVariable Long patientId) {
        return new ResponseEntity(findPatientCasesUseCase.find(patientId), HttpStatus.OK);
    }
}
