package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.CreatePatientUseCase;
import com.cob.billing.usecases.clinical.patient.FindPatientByNamUseCase;
import com.cob.billing.usecases.clinical.patient.FindPatientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    CreatePatientUseCase createPatientUseCase;
    @Autowired
    FindPatientUseCase findPatientUseCase;
    @Autowired
    FindPatientByNamUseCase findPatientByNamUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Patient model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient",
                        HttpStatus.OK,
                        createPatientUseCase.create(model));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all patients",
                        HttpStatus.OK, null,
                        findPatientUseCase.findAll(paging));
    }

    @GetMapping("find/patientId/{patientId}")
    public ResponseEntity findById(@PathVariable Long patientId) {
        return new ResponseEntity(findPatientUseCase.findById(patientId), HttpStatus.OK);
    }

    @GetMapping("find/name/{name}")
    public ResponseEntity findByName(@PathVariable String name) {
        return new ResponseEntity(findPatientByNamUseCase.find(name), HttpStatus.OK);
    }
}
