package com.cob.billing.controller.clinical;


import com.cob.billing.model.clinical.patient.ICD10DiagnosisResponse;
import com.cob.billing.service.CaseDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/case/diagnosis")
public class CaseDiagnosisController {

    @Autowired
    CaseDiagnosisService diagnosisService;
    @GetMapping(path = "/find/term/{term}")
    @ResponseBody
    public ResponseEntity retrieveDiagnosisByTerm(@PathVariable String term) {
        ICD10DiagnosisResponse diagnosis = null;
        try {
            diagnosis = diagnosisService.findByTerm(term);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(diagnosis);
    }
}
