package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.referring.provider.CreateReferringProviderUseCase;
import com.cob.billing.usecases.clinical.referring.provider.DeleteReferringProviderUseCase;
import com.cob.billing.usecases.clinical.referring.provider.RetrievingAllReferringProvidersUseCase;
import com.cob.billing.usecases.clinical.referring.provider.UpdateReferringProviderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/referring/provider")
@PreAuthorize("hasAnyRole('referring-provider-role')")
public class ReferringProviderController {

    @Autowired
    CreateReferringProviderUseCase createReferringProviderUseCase;
    @Autowired
    RetrievingAllReferringProvidersUseCase retrievingAllReferringProvidersUseCase;
    @Autowired
    DeleteReferringProviderUseCase deleteReferringProviderUseCase;
    @Autowired
    UpdateReferringProviderUseCase updateReferringProviderUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ReferringProvider referringProvider) {
        return ResponseHandler
                .generateResponse("Successfully added ReferringProvider",
                        HttpStatus.OK,
                        createReferringProviderUseCase.create(referringProvider));
    }
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ReferringProvider referringProvider) {
        return ResponseHandler
                .generateResponse("Successfully added ReferringProvider",
                        HttpStatus.OK,
                        updateReferringProviderUseCase.update(referringProvider));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Referring Provider!",
                        HttpStatus.OK, null,
                        retrievingAllReferringProvidersUseCase.findAll(paging));
    }

    @GetMapping("/find/all")
    public ResponseEntity findAll() {
        return new ResponseEntity(retrievingAllReferringProvidersUseCase.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler
                .generateResponse("Successfully added ReferringProvider",
                        HttpStatus.OK,
                        deleteReferringProviderUseCase.delete(id));
    }
}
