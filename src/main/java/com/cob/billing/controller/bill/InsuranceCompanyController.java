package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.usecases.bill.FindInsuranceCompanyListUseCase;
import com.cob.billing.usecases.bill.MapInsuranceCompanyToPayerUseCase;
import com.cob.billing.usecases.bill.insurance.company.FindInsuranceCompaniesUseCase;
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
    FindInsuranceCompanyListUseCase findInsuranceCompanyListUseCase;

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
        return new ResponseEntity(findInsuranceCompaniesUseCase.find(), HttpStatus.OK);
    }
    @GetMapping("/container/find")
    public ResponseEntity ddd(){
        return new ResponseEntity(findInsuranceCompanyListUseCase.find(), HttpStatus.OK);
    }
}
