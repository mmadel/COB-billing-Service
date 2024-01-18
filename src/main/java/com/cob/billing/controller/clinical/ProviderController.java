package com.cob.billing.controller.clinical;

import com.cob.billing.model.clinical.provider.Provider;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.clinical.provider.CreateProviderUseCase;
import com.cob.billing.usecases.clinical.provider.FindProvidersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/provider")
public class ProviderController {
    @Autowired
    CreateProviderUseCase createProviderUseCase;

    @Autowired
    FindProvidersUseCase findProvidersUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Provider provider) {
        return ResponseHandler
                .generateResponse("Successfully added Provider",
                        HttpStatus.OK,
                        createProviderUseCase.create(provider));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Providers!",
                        HttpStatus.OK, null,
                        findProvidersUseCase.findAll(paging));
    }

    @GetMapping("/find/all")
    public ResponseEntity findAll(){
        return new ResponseEntity(findProvidersUseCase.findAll(),HttpStatus.OK);
    }
}
