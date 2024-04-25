package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import com.cob.billing.usecases.bill.posting.client.batch.pdf.GenerateClientBatchReceiptPDFUseCase;
import com.cob.billing.usecases.bill.posting.batching.CreateBatchClientPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "/batch/payment")
public class BatchPaymentController {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;
    @Autowired
    CreateBatchClientPaymentUseCase createBatchClientPaymentUseCase;
    @Autowired
    GenerateClientBatchReceiptPDFUseCase generateClientBatchReceiptPDFUseCase;

    @PostMapping("/client/create")
    public ResponseEntity create(@RequestBody ServiceLinePaymentRequest model) {
        createBatchClientPaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/insurance-company/create")
    public ResponseEntity createInsuranceCompany(@RequestBody ServiceLinePaymentRequest model) {
        createSessionServiceLinePaymentUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/client/receipt/pdf")
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
