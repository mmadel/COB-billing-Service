package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.InsuranceCompanyConfiguration;
import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.usecases.FindInsuranceCompanyConfigurationUseCase;
import com.cob.billing.usecases.bill.CreateInsuranceCompanyConfigurationUseCase;
import com.cob.billing.usecases.bill.FindInsuranceCompaniesUseCase;
import com.cob.billing.usecases.bill.MapInsuranceCompanyToPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/insurance/company")
public class InsuranceCompanyController {

    @Autowired
    MapInsuranceCompanyToPayerUseCase mapInsuranceCompanyToPayerUseCase;

    @Autowired
    FindInsuranceCompaniesUseCase findInsuranceCompaniesUseCase;
    @Autowired
    CreateInsuranceCompanyConfigurationUseCase createInsuranceCompanyConfigurationUseCase;
    @Autowired
    FindInsuranceCompanyConfigurationUseCase findInsuranceCompanyConfigurationUseCase;


    @PutMapping("/map/all")
    public ResponseEntity mapAll(@RequestBody List<InsuranceCompanyMapper> models) {
        mapInsuranceCompanyToPayerUseCase.mapAll(models);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/map")
    public ResponseEntity map(@RequestBody InsuranceCompanyMapper model) {
        mapInsuranceCompanyToPayerUseCase.map(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity findAll() {
        //findInsuranceCompaniesUseCase.find()
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/container/find")
    public ResponseEntity findContainer() {
        //findInsuranceCompaniesUseCase.find(),
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/configure")
    public ResponseEntity configure(@RequestBody InsuranceCompanyConfiguration model) {
        createInsuranceCompanyConfigurationUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/configuration/identifier/{identifier}")
    public ResponseEntity findConfiguration(@PathVariable Long identifier) {
        return new ResponseEntity(findInsuranceCompanyConfigurationUseCase.find(identifier),
                HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity findByName(@PathVariable String name) {
        //findInsuranceCompanyUseCase.find(name),
        return new ResponseEntity(HttpStatus.OK);
    }
}
