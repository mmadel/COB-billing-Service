package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.balance.PatientBalanceSettings;
import com.cob.billing.usecases.bill.posting.balance.CreateOrUpdateClientBalanceSettingsUseCase;
import com.cob.billing.usecases.bill.posting.balance.RetrieveClientBalanceSettingsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client/balance/settings")
public class ClientBalanceSettingsController {
    @Autowired
    CreateOrUpdateClientBalanceSettingsUseCase createOrUpdateClientBalanceSettingsUseCase;
    @Autowired
    RetrieveClientBalanceSettingsUseCase retrieveClientBalanceSettingsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PatientBalanceSettings model) {
        return new ResponseEntity(createOrUpdateClientBalanceSettingsUseCase.create(model), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity find() {
        return new ResponseEntity(retrieveClientBalanceSettingsUseCase.retrieve(), HttpStatus.OK);
    }
}
