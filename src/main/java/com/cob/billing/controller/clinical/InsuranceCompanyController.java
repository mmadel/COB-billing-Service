package com.cob.billing.controller.clinical;

import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.usecases.clinical.patient.insurance.company.CreateInsuranceCompanyConfigurationUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.FindInsuranceCompaniesUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.FindInsuranceCompanyConfigurationTmpUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.MapInsuranceCompanyToPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/insurance/company")
public class InsuranceCompanyController {


    @Autowired
    FindInsuranceCompaniesUseCase findInsuranceCompaniesUseCase;
    @Autowired
    CreateInsuranceCompanyConfigurationUseCase createInsuranceCompanyConfigurationUseCase;
    @Autowired
    FindInsuranceCompanyConfigurationTmpUseCase findInsuranceCompanyConfigurationUseCase;
    @Autowired
    MapInsuranceCompanyToPayerUseCase mapInsuranceCompanyToPayerUseCase;


    @PostMapping("/map")
    public ResponseEntity map(@RequestBody InsuranceCompanyMapper model) {
        mapInsuranceCompanyToPayerUseCase.map(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity findAll() {
        return new ResponseEntity(findInsuranceCompaniesUseCase.find(), HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity findAll(@PathVariable String name) {
        return new ResponseEntity(findInsuranceCompaniesUseCase.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/find/internal")
    public ResponseEntity findInternal() {
        return new ResponseEntity(findInsuranceCompaniesUseCase.findInternal(), HttpStatus.OK);
    }

    @GetMapping("/find/internal/id/{id}")
    public ResponseEntity findInternalPayer(@PathVariable Long id) {
        return new ResponseEntity(findInsuranceCompaniesUseCase.findInternalPayer(id), HttpStatus.OK);
    }

    @PostMapping("/configure")
    public ResponseEntity configure(@RequestBody InsuranceCompanyConfiguration model) {
        createInsuranceCompanyConfigurationUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/element/configuration/id/{id}/visibility/{visibility}")
    public ResponseEntity findelementConfiguration(@PathVariable Long id, @PathVariable String visibility) {
        return new ResponseEntity(findInsuranceCompanyConfigurationUseCase.findElement(id, InsuranceCompanyVisibility.valueOf(visibility)),
                HttpStatus.OK);
    }
    @GetMapping("/find/configuration/id/{id}/visibility/{visibility}")
    public ResponseEntity findConfiguration(@PathVariable Long id, @PathVariable String visibility) {
        return new ResponseEntity(findInsuranceCompanyConfigurationUseCase.find(id, InsuranceCompanyVisibility.valueOf(visibility)),
                HttpStatus.OK);
    }
}
