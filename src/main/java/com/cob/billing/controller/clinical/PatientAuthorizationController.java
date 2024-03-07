package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authorization")
public class PatientAuthorizationController {

    public ResponseEntity<Object> create(@RequestBody PatientAuthorization model) {
        return null;
    }
}
