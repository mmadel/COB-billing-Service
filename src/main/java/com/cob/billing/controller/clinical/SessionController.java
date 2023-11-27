package com.cob.billing.controller.clinical;

import com.cob.billing.response.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session")
public class SessionController {
    @PostMapping("/create")
    public ResponseEntity<Object> create(){
        return ResponseHandler
                .generateResponse("Successfully added Patient Session",
                        HttpStatus.OK, );
    }
}
