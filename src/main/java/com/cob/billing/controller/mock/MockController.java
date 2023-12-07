package com.cob.billing.controller.mock;

import com.cob.billing.model.bill.insurance.company.InsuranceCompany;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.usecases.bill.insurance.company.AddInsuranceCompanyUseCase;
import com.cob.billing.usecases.bill.payer.AddPayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockController {
    @Autowired
    AddInsuranceCompanyUseCase addInsuranceCompanyUseCase;
    @Autowired
    AddPayerUseCase addPayerUseCase;

    @PostMapping("/insurance/company/create")
    public ResponseEntity createInsuranceCompany(@RequestBody List<InsuranceCompany> insuranceCompanies) {
        addInsuranceCompanyUseCase.add(insuranceCompanies);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/payer/create")
    public ResponseEntity createPayer(@RequestBody List<Payer> payers) {
        addPayerUseCase.add(payers);
        return new ResponseEntity(HttpStatus.OK);
    }
}
