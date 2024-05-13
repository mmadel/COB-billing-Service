package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.referring.provider.CreateReferringProviderUseCase;
import com.cob.billing.usecases.clinical.referring.provider.RetrievingAllReferringProvidersUseCase;
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

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ReferringProvider referringProvider) {
        return ResponseHandler
                .generateResponse("Successfully added ReferringProvider",
                        HttpStatus.OK,
                        createReferringProviderUseCase.create(referringProvider));
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
    public ResponseEntity findAll(){
        return new ResponseEntity(retrievingAllReferringProvidersUseCase.findAll(),HttpStatus.OK);
    }
}
