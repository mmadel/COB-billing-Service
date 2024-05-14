package com.cob.billing.controller.bill;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.InvoiceGenerationResponse;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedSessionsByPatientUseCase;
import com.cob.billing.usecases.bill.invoice.FindNotSubmittedSessionsGroupByPatientsUseCase;
import com.cob.billing.usecases.bill.invoice.cms.DownLoadCMSUseCase;
import com.cob.billing.usecases.bill.invoice.cms.GenerateCMSInvoiceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.UploadCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.GenerateElectronicInvoiceUseCase;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.zip.DataFormatException;

@CrossOrigin
@RestController
@RequestMapping(value = "/invoice")
@PreAuthorize("hasAnyRole('billing-role','invoice-billing-role')")
public class InvoiceController {
    @Autowired
    FindNotSubmittedSessionsGroupByPatientsUseCase findNotSubmittedSessionsGroupByPatientsUseCase;
    @Autowired
    FindNotSubmittedSessionsByPatientUseCase findNotSubmittedSessionsByPatientUseCase;
    @Autowired
    GenerateCMSInvoiceUseCase generateCMSInvoiceUseCase;
    @Autowired
    GenerateElectronicInvoiceUseCase generateElectronicInvoiceUseCase;
    @Autowired
    UploadCMSFileUseCase uploadCMSFileUseCase;
    @Autowired
    DownLoadCMSUseCase downLoadCMSUseCase;

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
    public ResponseEntity create(@RequestBody InvoiceRequest invoiceRequest,
                                 HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, AuthorizationException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline");
        List<String> files;
        InvoiceGenerationResponse invoiceGenerationResponse = generateCMSInvoiceUseCase.generate(invoiceRequest);
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for (String file : invoiceGenerationResponse.getFiles()) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
        }
        merger.close();
        uploadCMSFileUseCase.persist(invoiceGenerationResponse.getFiles(), invoiceGenerationResponse.getRecords());
        merger.close();
        return new ResponseEntity(HttpStatus.OK);
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

    @PostMapping(value = "/download/cms/invoice/{submissionId}")
    public ResponseEntity<byte[]> downloadCMSDocument(@PathVariable("submissionId") Long submissionId,
                                                      HttpServletResponse response) throws IOException, DataFormatException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cms.pdf");
        return new ResponseEntity<>(downLoadCMSUseCase.download(submissionId).getByteArray(), headers, HttpStatus.OK);
    }
}
