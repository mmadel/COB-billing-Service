package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.usecases.bill.posting.ConstructServiceLinesPaymentsUseCase;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import com.cob.billing.usecases.bill.posting.GenerateClientBatchReceiptPDFUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

@RestController
@RequestMapping(value = "/session/payment")
public class EnterPaymentController {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;
    @Autowired
    ConstructServiceLinesPaymentsUseCase constructServiceLinesPaymentsUseCase;
    @Autowired
    GenerateClientBatchReceiptPDFUseCase generateClientBatchReceiptPDFUseCase;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/{serviceLinesIds}")
    public ResponseEntity find(@PathVariable Long[] serviceLinesIds) {
        return new ResponseEntity(constructServiceLinesPaymentsUseCase.construct(Arrays.asList(serviceLinesIds)), HttpStatus.OK);
    }

    @PostMapping("/pdf")
    public void exportPdf( HttpServletResponse response) throws IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/pdf");
        byte[] pdfAsBytes = generateClientBatchReceiptPDFUseCase.create();
        response.setContentLength(pdfAsBytes.length);
        OutputStream os = response.getOutputStream();
        os.write(pdfAsBytes);
        os.flush();
        os.close();
    }
}
