package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.electronic.GenerateElectronicInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.GenerateCMSInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedPatientSessionUseCase;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    FindNotSubmittedPatientSessionUseCase findNotSubmittedPatientSessionUseCase;
    @Autowired
    GenerateCMSInvoiceUseCase generateCMSInvoiceUseCase;
    @Autowired
    GenerateElectronicInvoiceUseCase generateElectronicInvoiceUseCase;

    @GetMapping("/find/clients")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully finding  patients with session status prepared",
                        HttpStatus.OK, null, findNotSubmittedPatientSessionUseCase.findNotSubmittedSession(paging));
    }

    @GetMapping("/find/clientId/{clientId}")
    public ResponseEntity find(@PathVariable Long clientId) {
        return new ResponseEntity<>(findNotSubmittedPatientSessionUseCase.findNotSubmittedSessionByPatient(clientId), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public void create(@RequestBody InvoiceRequest invoiceRequest,
                       HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline");
        List<String> files = generateCMSInvoiceUseCase.generate(invoiceRequest);
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for (String file : files) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
            tmpFile.delete();
        }
        merger.close();
    }

    @PostMapping("/create/electronic")
    public ResponseEntity<Object> createElectronic(@RequestBody InvoiceRequest invoiceRequest,
                                                   HttpServletResponse response) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "inline");
        Gson gson = new Gson();
        String toJson = gson.toJson(generateElectronicInvoiceUseCase.generate(invoiceRequest));
        return new ResponseEntity<>(toJson, HttpStatus.OK);
    }
}
