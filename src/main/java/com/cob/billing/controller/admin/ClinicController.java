package com.cob.billing.controller.admin;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.admin.clinic.CreateClinicUseCase;
import com.cob.billing.usecases.admin.clinic.FindClinicUseCase;
import com.cob.billing.usecases.admin.clinic.UpdateClinicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clinic")
public class ClinicController {
    @Autowired
    CreateClinicUseCase createClinicUseCase;

    @Autowired
    UpdateClinicUseCase updateClinicUseCase;

    @Autowired
    FindClinicUseCase findClinicUseCase;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Clinic model) {
        return ResponseHandler
                .generateResponse("Successfully added clinic",
                        HttpStatus.OK,
                        createClinicUseCase.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Clinic model) {
        return ResponseHandler
                .generateResponse("Successfully updated clinic",
                        HttpStatus.OK,
                        updateClinicUseCase.update(model));
    }
    @GetMapping("/find")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all patients",
                        HttpStatus.OK, null,
                        findClinicUseCase.findAll(paging));
    }
}
