package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.PatientSearchCriteria;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.model.clinical.patient.update.profile.UpdateProfileDTO;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.*;
import com.cob.billing.usecases.clinical.patient.insurance.company.DeletePatientInsuranceCompanyUseCase;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Autowired
    ChangePatientStatusUseCase changePatientStatusUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Patient model) {
        log.info("Create patient {}", model);
        return ResponseHandler
                .generateResponse("Successfully added Patient",
                        HttpStatus.OK,
                        createPatientUseCase.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UpdateProfileDTO profile) {
        log.info("update patient {}", profile);
        updatePatientUseCase.update(profile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
                                          @PathVariable(name = "status") boolean status) {
        log.info("list patients offset {} ,limit {} , status {} ", offset, limit, status);
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all patients",
                        HttpStatus.OK, null,
                        findPatientUseCase.findAll(paging, status));
    }

    @PostMapping("/find/filter")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit
            , @RequestBody PatientSearchCriteria searchCriteria) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        log.info("list filter patients offset {} ,limit {} , PatientSearchCriteria {} ", offset, limit, searchCriteria);
        return ResponseHandler
                .generateResponse("Successfully find filtered patients",
                        HttpStatus.OK, null, findFilteredPatientUseCase.find(paging, searchCriteria)
                );
    }

    @GetMapping("find/patientId/{patientId}")
    public ResponseEntity findById(@PathVariable Long patientId) {
        log.info("Find patient by id {}", patientId);
        return new ResponseEntity(findPatientUseCase.findById(patientId), HttpStatus.OK);
    }

    @GetMapping("find/name/{name}")
    public ResponseEntity findByName(@PathVariable String name) {
        log.info("Find patient by name {}", name);
        return new ResponseEntity(findPatientByNamUseCase.find(name), HttpStatus.OK);
    }

    @GetMapping("find/first/{first}/last/{last}")
    public ResponseEntity findByFirstNameAndLastName(@PathVariable String first, @PathVariable String last) {
        log.info("Find patient by FirstName {} and LastName", first, last);
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

    @PutMapping("/change-status/patientId/{patientId}/{status}")
    public ResponseEntity changePatientStatus(@PathVariable Long patientId, @PathVariable Boolean status) {
        changePatientStatusUseCase.changeStatus(patientId, status);
        return new ResponseEntity(HttpStatus.OK);
    }

}
