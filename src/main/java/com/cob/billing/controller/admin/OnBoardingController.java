package com.cob.billing.controller.admin;

import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.usecases.admin.onboarding.SetupOrganizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/onboarding")
public class OnBoardingController {
    @Autowired
    SetupOrganizationUseCase setupOrganizationUseCase;

    @PostMapping("/setup")
    public ResponseEntity<Object> create(@RequestBody Organization model) throws OrganizationException {
        setupOrganizationUseCase.setup(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
