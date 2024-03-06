package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedSessionsByPatientUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.GenerateElectronicInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.GenerateCMSInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedSessionsGroupByPatientsUseCase;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
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
    FindNotSubmittedSessionsGroupByPatientsUseCase findNotSubmittedSessionsGroupByPatientsUseCase;
    @Autowired
    FindNotSubmittedSessionsByPatientUseCase findNotSubmittedSessionsByPatientUseCase;
    @Autowired
    GenerateCMSInvoiceUseCase generateCMSInvoiceUseCase;
    @Autowired
    GenerateElectronicInvoiceUseCase generateElectronicInvoiceUseCase;

    @GetMapping("/find")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit) {
        return ResponseHandler
                .generateResponse("Successfully find clients with not submitted sessions",
                        HttpStatus.OK, null,
                        findNotSubmittedSessionsGroupByPatientsUseCase.find(offset + 1, limit));
    }

    @GetMapping("/find/patient/{patientId}")
    public ResponseEntity<Object> findByClient(@RequestParam(name = "offset") int offset,
                                               @RequestParam(name = "limit") int limit
            , @PathVariable(name = "patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully find clients with not submitted sessions",
                        HttpStatus.OK, null,
                        findNotSubmittedSessionsByPatientUseCase.find(offset + 1, limit, patientId));
    }

    @PostMapping("/find/patient/{patientId}")
    public ResponseEntity<Object> findByClientFiltered(@RequestParam(name = "offset") int offset,
                                                           @RequestParam(name = "limit") int limit
            , @PathVariable(name = "patientId") Long patientId
            , @RequestBody PatientSessionSearchCriteria patientSessionSearchCriteria) {
        return ResponseHandler
                .generateResponse("Successfully find clients with not submitted sessions",
                        HttpStatus.OK, null,
                        findNotSubmittedSessionsByPatientUseCase.findFiltered(offset + 1, limit, patientId, patientSessionSearchCriteria));
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
