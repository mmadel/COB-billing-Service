package com.cob.billing.controller.integration;

import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.era.FindClaimAdjustmentReasonUseCase;
import com.cob.billing.usecases.bill.invoice.UpdateSubmittedClaimStatus;
import com.cob.billing.usecases.bill.posting.era.FetchERADetailsUseCase;
import com.cob.billing.usecases.bill.posting.era.FetchERAListUseCase;
import com.cob.billing.usecases.integration.claim.md.CacheClaimMDResponseDataUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveClaimsHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ch")
public class ClaimMDController {

    @Autowired
    RetrieveClaimsHistoryUseCase retrieveClaimsHistoryUseCase;
    @Autowired
    UpdateSubmittedClaimStatus updateSubmittedClaimStatus;
    @Autowired
    CacheClaimMDResponseDataUseCase cacheClaimMDResponseDataUseCase;
    @Autowired
    FetchERAListUseCase fetchERAListUseCase;
    @Autowired
    FetchERADetailsUseCase fetchERADetailsUseCase;
    @Autowired
    FindClaimAdjustmentReasonUseCase findClaimAdjustmentReasonUseCase;

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
        cacheClaimMDResponseDataUseCase.updateCachedNumber(responseId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/response")
    public ResponseEntity get() {
        return new ResponseEntity(cacheClaimMDResponseDataUseCase.getCachedNumber(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/response")
    public ResponseEntity delete() {
        cacheClaimMDResponseDataUseCase.clearCache();
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

    @GetMapping("/claim/code/{code}")
    public ResponseEntity getByCode(@PathVariable String code){
        return new ResponseEntity(findClaimAdjustmentReasonUseCase.find(code), HttpStatus.OK);
    }

    @GetMapping("/claim/codes/{codes}")
    public ResponseEntity getByCodes(@PathVariable List<String> codes){
        return new ResponseEntity(findClaimAdjustmentReasonUseCase.findByCodes(codes), HttpStatus.OK);
    }
}
