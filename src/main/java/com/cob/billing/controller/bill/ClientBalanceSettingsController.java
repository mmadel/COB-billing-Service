package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.balance.PatientBalanceSettings;
import com.cob.billing.usecases.bill.posting.balance.CreateOrUpdateClientBalanceSettingsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client/balance/settings")
public class ClientBalanceSettingsController {
    @Autowired
    CreateOrUpdateClientBalanceSettingsUseCase createOrUpdateClientBalanceSettingsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PatientBalanceSettings model) {
        return new ResponseEntity(createOrUpdateClientBalanceSettingsUseCase.create(model), HttpStatus.OK);
    }
}
