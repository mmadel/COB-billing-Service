package com.cob.billing.controller.admin;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.admin.organization.CreateOrganizationUseCase;
import com.cob.billing.usecases.admin.organization.RetrievingOrganizationUseCase;
import com.cob.billing.usecases.admin.organization.UpdateOrganizationUseCase;
import com.cob.billing.usecases.security.DisableUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/organization")

public class OrganizationController {
    @Autowired
    CreateOrganizationUseCase createOrganizationUseCase;
    @Autowired
    UpdateOrganizationUseCase updateOrganizationUseCase;
    @Autowired
    RetrievingOrganizationUseCase retrievingOrganizationUseCase;
    @Autowired
    DisableUserUseCase disableUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Organization model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        return ResponseHandler
                .generateResponse("Successfully added Organization",
                        HttpStatus.OK,
                        createOrganizationUseCase.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Organization model) {
        return ResponseHandler
                .generateResponse("Successfully updated Organization",
                        HttpStatus.OK,
                        updateOrganizationUseCase.update(model));
    }

    @GetMapping("/find/default")
    @PreAuthorize("hasAnyRole('billing-role','group-info-admin-tool-role')")
    public ResponseEntity findAll() {
        return new ResponseEntity(retrievingOrganizationUseCase.findDefault(), HttpStatus.OK);
    }

    @PutMapping("/disable/uuid/{uuid}")
    public ResponseEntity disable(@PathVariable("uuid") String uuid){
        disableUserUseCase.disable(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
