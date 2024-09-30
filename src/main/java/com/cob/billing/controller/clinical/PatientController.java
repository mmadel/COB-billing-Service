package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.PatientSearchCriteria;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.model.clinical.patient.update.profile.UpdateProfileDTO;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.*;
import com.cob.billing.usecases.clinical.patient.insurance.company.DeletePatientInsuranceCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient")
@PreAuthorize("hasAnyRole('patient-role')")
public class PatientController {
    @Autowired
    CreatePatientUseCase createPatientUseCase;
    @Autowired
    FindPatientUseCase findPatientUseCase;
    @Autowired
    FindPatientByNamUseCase findPatientByNamUseCase;
    @Autowired
    FindPatientCasesUseCase findPatientCasesUseCase;
    @Autowired
    DeletePatientInsuranceCompanyUseCase deletePatientInsuranceCompanyUseCase;
    @Autowired
    FlagPatientAuthorizationUseCase flagPatientAuthorizationUseCase;
    @Autowired
    FindPatientInsuranceCompanyUseCase findPatientInsuranceCompanyUseCase;
    @Autowired
    UpdatePatientUseCase updatePatientUseCase;
    @Autowired
    FindFilteredPatientUseCase findFilteredPatientUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Patient model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient",
                        HttpStatus.OK,
                        createPatientUseCase.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UpdateProfileDTO profile) {
        updatePatientUseCase.update(profile);
        return new ResponseEntity(HttpStatus.OK);
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

    @PostMapping("/find/filter")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit
            , @RequestBody PatientSearchCriteria searchCriteria) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find filtered patients",
                        HttpStatus.OK, null, findFilteredPatientUseCase.find(paging, searchCriteria)
                );
    }

    @GetMapping("find/patientId/{patientId}")
    public ResponseEntity findById(@PathVariable Long patientId) {
        return new ResponseEntity(findPatientUseCase.findById(patientId), HttpStatus.OK);
    }

    @GetMapping("find/name/{name}")
    public ResponseEntity findByName(@PathVariable String name) {
        return new ResponseEntity(findPatientByNamUseCase.find(name), HttpStatus.OK);
    }

    @GetMapping("find/first/{first}/last/{last}")
    public ResponseEntity findByFirstNameAndLastName(@PathVariable String first, @PathVariable String last) {
        return new ResponseEntity(findPatientByNamUseCase.findByFirstAndLastName(first, last), HttpStatus.OK);
    }

    @GetMapping("/auth/off/patientId/{patientId}")
    public ResponseEntity turnOffPatientAuthorization(@PathVariable Long patientId) {
        flagPatientAuthorizationUseCase.turnOff(patientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/auth/on/patientId/{patientId}")
    public ResponseEntity turnOnPatientAuthorization(@PathVariable Long patientId) {
        flagPatientAuthorizationUseCase.turnOn(patientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/insurance-company/patientId/{patientId}")
    public ResponseEntity findPatientInsuranceCompany(@PathVariable Long patientId) {
        return new ResponseEntity(findPatientInsuranceCompanyUseCase.find(patientId), HttpStatus.OK);
    }

}
