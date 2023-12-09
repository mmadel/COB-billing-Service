package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.usecases.bill.MapInsuranceCompanyToPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/insurance/company")
public class InsuranceCompanyController {

    @Autowired
    MapInsuranceCompanyToPayerUseCase mapInsuranceCompanyToPayerUseCase;

    @PutMapping("/map")
    public ResponseEntity map(@RequestBody List<InsuranceCompanyMapper> models) {
        mapInsuranceCompanyToPayerUseCase.map(models);
        return new ResponseEntity(HttpStatus.OK);
    }
}
