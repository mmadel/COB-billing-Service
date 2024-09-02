package com.cob.billing.controller.integration;

import com.cob.billing.entity.bill.invoice.tmp.PatientInvoiceRecord;
import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.repositories.bill.invoice.tmp.PatientInvoiceRecordRepository;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.usecases.bill.invoice.UpdateSubmittedClaimStatus;
import com.cob.billing.usecases.integration.claim.md.CacheResponseIdUseCase;
import com.cob.billing.usecases.integration.claim.md.GetClaimsHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ch")
public class ClaimMDController {

    @Autowired
    GetClaimsHistoryUseCase getClaimsHistoryUseCase;
    @Autowired
    UpdateSubmittedClaimStatus findBySubmissionStatus;
    @Autowired
    CacheResponseIdUseCase cacheResponseIdUseCase;

    @GetMapping("/get/responseId/{responseId}")
    public ResponseEntity get(@PathVariable Long responseId) {
        return new ResponseEntity(getClaimsHistoryUseCase.get(responseId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity findByStatus() {
        findBySubmissionStatus.update();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update/response/id/{responseId}")
    public ResponseEntity findByStatus(@PathVariable Long responseId) {
        cacheResponseIdUseCase.updateCachedNumber(responseId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/response")
    public ResponseEntity get(){
        return new ResponseEntity(cacheResponseIdUseCase.getCachedNumber(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/response")
    public ResponseEntity delete(){
        cacheResponseIdUseCase.clearCache();
        return new ResponseEntity(HttpStatus.OK);
    }
}
