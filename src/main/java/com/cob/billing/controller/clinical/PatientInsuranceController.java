package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.patient.insurance.company.CreatePatientInsuranceCompanyUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.DeletePatientInsuranceCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/insurance/company")
public class PatientInsuranceController {
    @Autowired
    DeletePatientInsuranceCompanyUseCase deletePatientInsuranceCompanyUseCase;
    @Autowired
    CreatePatientInsuranceCompanyUseCase createPatientInsuranceCompanyUseCase;

    @DeleteMapping("/delete/{id}/visibility/{visibility}")
    public ResponseEntity<Object> delete(@PathVariable Long id, @PathVariable String visibility) {
        return ResponseHandler
                .generateResponse("Successfully added Patient",
                        HttpStatus.OK,
                        deletePatientInsuranceCompanyUseCase.delete(id, InsuranceCompanyVisibility.valueOf(visibility)));
    }

    @PostMapping("/create/patient/{patientId}")
    public ResponseEntity<Object> create(@RequestBody PatientInsurance model, @PathVariable Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully added Patient Insurance",
                        HttpStatus.OK,
                        createPatientInsuranceCompanyUseCase.create(model, patientId));
    }
}
