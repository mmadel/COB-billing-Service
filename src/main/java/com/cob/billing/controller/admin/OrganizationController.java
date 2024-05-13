package com.cob.billing.controller.admin;

import com.cob.billing.model.admin.Organization;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.admin.organization.CreateOrganizationUseCase;
import com.cob.billing.usecases.admin.organization.RetrievingOrganizationUseCase;
import com.cob.billing.usecases.admin.organization.UpdateOrganizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/organization")
@PreAuthorize("hasAnyRole('admin-tool-role')")
public class OrganizationController {
    @Autowired
    CreateOrganizationUseCase createOrganizationUseCase;
    @Autowired
    UpdateOrganizationUseCase updateOrganizationUseCase;
    @Autowired
    RetrievingOrganizationUseCase retrievingOrganizationUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Organization model) {
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
    @PreAuthorize("hasAnyRole('billing-role')")
    public ResponseEntity findAll() {
        return new ResponseEntity(retrievingOrganizationUseCase.findDefault(), HttpStatus.OK);
    }
}
