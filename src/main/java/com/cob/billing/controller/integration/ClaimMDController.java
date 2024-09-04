package com.cob.billing.controller.integration;

import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.UpdateSubmittedClaimStatus;
import com.cob.billing.usecases.bill.posting.era.FetchERADetailsUseCase;
import com.cob.billing.usecases.bill.posting.era.FetchERAListUseCase;
import com.cob.billing.usecases.integration.claim.md.CacheResponseIdUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveClaimsHistoryUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveERADetailsUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveERAListUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ch")
public class ClaimMDController {

    @Autowired
    RetrieveClaimsHistoryUseCase retrieveClaimsHistoryUseCase;
    @Autowired
    UpdateSubmittedClaimStatus updateSubmittedClaimStatus;
    @Autowired
    CacheResponseIdUseCase cacheResponseIdUseCase;
    @Autowired
    FetchERAListUseCase fetchERAListUseCase;
    @Autowired
    FetchERADetailsUseCase fetchERADetailsUseCase;

    @GetMapping("/get/responseId/{responseId}")
    public ResponseEntity get(@PathVariable Long responseId) {
        return new ResponseEntity(retrieveClaimsHistoryUseCase.get(responseId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity findByStatus() {
        updateSubmittedClaimStatus.update();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update/response/id/{responseId}")
    public ResponseEntity findByStatus(@PathVariable Long responseId) {
        cacheResponseIdUseCase.updateCachedNumber(responseId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/response")
    public ResponseEntity get() {
        return new ResponseEntity(cacheResponseIdUseCase.getCachedNumber(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/response")
    public ResponseEntity delete() {
        cacheResponseIdUseCase.clearCache();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/era/list")
    public ResponseEntity getERAList(@RequestParam(name = "offset") String offset,
                                     @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find ",
                        HttpStatus.OK, null,
                        fetchERAListUseCase.fetch(paging));
    }

    @GetMapping("era/detials/{eraid}")
    public ResponseEntity get(@PathVariable Integer eraid) {
        return new ResponseEntity(fetchERADetailsUseCase.fetch(eraid), HttpStatus.OK);
    }
}
