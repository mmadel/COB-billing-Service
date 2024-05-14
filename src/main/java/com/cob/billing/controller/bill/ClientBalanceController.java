package com.cob.billing.controller.bill;


import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.usecases.bill.posting.balance.FindClientPendingServiceLinesUseCase;
import com.cob.billing.usecases.bill.posting.balance.FindFinalizedServiceLinesUseCase;
import com.cob.billing.usecases.bill.posting.balance.pdf.GenerateClientBalanceStatementPDFUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "/client/balance")
@PreAuthorize("hasAnyRole('payment-role','balance-statement-payment-role')")
public class ClientBalanceController {
    @Autowired
    FindClientPendingServiceLinesUseCase findClientPendingServiceLinesUseCase;
    @Autowired
    FindFinalizedServiceLinesUseCase findFinalizedServiceLinesUseCase;
    @Autowired
    GenerateClientBalanceStatementPDFUseCase generateClientBalanceStatementPDFUseCase;

    @PostMapping("/find/awaiting/patient/{patientId}")
    public ResponseEntity findAwaiting(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit,
                                       @PathVariable(name = "patientId") Long patientId
            , @RequestBody PatientSessionSearchCriteria patientSessionSearchCriteria) {
        return new ResponseEntity(findClientPendingServiceLinesUseCase.find(offset + 1, limit, patientId, patientSessionSearchCriteria), HttpStatus.OK);
    }

    @PostMapping("/find/finalized/patient/{patientId}")
    public ResponseEntity findFinalized(@RequestParam(name = "offset") int offset,
                                        @RequestParam(name = "limit") int limit,
                                        @PathVariable(name = "patientId") Long patientId
            , @RequestBody PatientSessionSearchCriteria patientSessionSearchCriteria) {
        return new ResponseEntity(findFinalizedServiceLinesUseCase.find(offset + 1, limit, patientId, patientSessionSearchCriteria), HttpStatus.OK);
    }

    @PostMapping("/pdf")
    public void exportPdf(@RequestBody ClientBalanceInvoice clientBalanceInvoice, HttpServletResponse response) throws IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/pdf");
        byte[] byteArray = generateClientBalanceStatementPDFUseCase.generate(clientBalanceInvoice);
        response.setContentLength(byteArray.length);
        OutputStream os = response.getOutputStream();
        os.write(byteArray);
        os.flush();
        os.close();
    }
}
