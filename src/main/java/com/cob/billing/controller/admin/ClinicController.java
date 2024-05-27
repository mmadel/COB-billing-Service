package com.cob.billing.controller.admin;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.admin.clinic.CreateClinicUseCase;
import com.cob.billing.usecases.admin.clinic.DeleteClinicUseCase;
import com.cob.billing.usecases.admin.clinic.FindClinicUseCase;
import com.cob.billing.usecases.admin.clinic.UpdateClinicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clinic")
@PreAuthorize("hasAnyRole('admin-tool-role','group-info-admin-tool-role')")
public class ClinicController {
    @Autowired
    CreateClinicUseCase createClinicUseCase;

    @Autowired
    UpdateClinicUseCase updateClinicUseCase;

    @Autowired
    FindClinicUseCase findClinicUseCase;
    @Autowired
    DeleteClinicUseCase deleteClinicUseCase;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Clinic model) {
        return ResponseHandler
                .generateResponse("Successfully added clinic",
                        HttpStatus.OK,
                        createClinicUseCase.create(model));
    }

    @DeleteMapping("/delete/clinicId/{clinicId}")
    public ResponseEntity<Object> delete(@PathVariable Long clinicId){
        return ResponseHandler
                .generateResponse("Successfully added clinic",
                        HttpStatus.OK,
                        deleteClinicUseCase.delete(clinicId));
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
                .generateResponse("Successfully find all clinics",
                        HttpStatus.OK, null,
                        findClinicUseCase.findAll(paging));
    }
    @GetMapping("/find/all")
    public ResponseEntity findAll(){
        return new ResponseEntity(findClinicUseCase.findAll(),HttpStatus.OK);
    }
}
