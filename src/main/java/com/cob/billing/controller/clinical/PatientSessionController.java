package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.clinical.patient.session.CreatePatientSessionUseCase;
import com.cob.billing.usecases.clinical.patient.session.FindSessionByPatientUseCase;
import com.cob.billing.usecases.clinical.patient.session.UpdatePatientSessionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/find/patientId/{patientId}")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit
            , @PathVariable Long patientId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding Patient Session",
                        HttpStatus.OK, null, findSessionByPatientUseCase.find(paging, patientId));
    }

    @PutMapping("/change/status/serviceCode/{serviceCodeId}")
    public ResponseEntity changeStatus(@PathVariable Long serviceCodeId) {
        changeSessionStatusUseCase.change(serviceCodeId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
