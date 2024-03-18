package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.checker.ServiceLineExceedChunkChecker;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.finder.ClinicModelFinder;
import com.cob.billing.usecases.bill.invoice.cms.finder.ProviderModelFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClaimCreator {
    @Autowired
    private CreateCMSPdfDocumentResourceUseCase createCMSPdfDocumentResourceUseCase;
    @Autowired
    private NotRepeatableCMSDocumentFiller notRepeatableCMSDocumentFiller;
    @Autowired
    private ServiceLineCMSDocumentFiller serviceLineCMSDocumentFiller;
    @Autowired
    private PhysicianCMSDocumentFiller physicianCMSDocumentFiller;
    @Autowired
    private LocationCMSDocumentFiller locationCMSDocumentFiller;

    private InvoiceRequest invoiceRequest;

    public List<String> create() throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(invoiceRequest.getSelectedSessionServiceLine());
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "claim.pdf" + "_" + i;
            createBasicClaimPart(fileName, invoicesChunk);
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public List<String> create(DoctorInfo provider, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(selectedSessionServiceLine);
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "provider_" + provider.getDoctorNPI() + "_" + i;
            createCMSPdfDocumentResourceUseCase.createResource(fileName);
            notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
            serviceLineCMSDocumentFiller.create(invoicesChunk, createCMSPdfDocumentResourceUseCase.getForm());
            physicianCMSDocumentFiller.create(provider, createCMSPdfDocumentResourceUseCase.getForm());
            locationCMSDocumentFiller.create(ClinicModelFinder.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
            createCMSPdfDocumentResourceUseCase.lockForm();
            createCMSPdfDocumentResourceUseCase.closeResource();
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public List<String> create(Clinic clinic, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(selectedSessionServiceLine);
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; serviceLinesChunks.size() > i; i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "clinic_" + clinic.getNpi() + "_" + i;
            createCMSPdfDocumentResourceUseCase.createResource(fileName);
            notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
            serviceLineCMSDocumentFiller.create(invoicesChunk, createCMSPdfDocumentResourceUseCase.getForm());
            physicianCMSDocumentFiller.create(ProviderModelFinder.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
            locationCMSDocumentFiller.create(clinic, createCMSPdfDocumentResourceUseCase.getForm());
            createCMSPdfDocumentResourceUseCase.lockForm();
            createCMSPdfDocumentResourceUseCase.closeResource();
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public List<String> create(String caseTitle, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(selectedSessionServiceLine);
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "case_" + caseTitle + "_" + i;
            createBasicClaimPart(fileName, invoicesChunk);
            fileNames.add(fileName);
        }
        return fileNames;
    }
    public List<String> createAuths(String authorizationNumber, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(selectedSessionServiceLine);
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "auth_" + authorizationNumber + "_" + i;
            createBasicClaimPart(fileName, invoicesChunk);
            fileNames.add(fileName);
        }
        return fileNames;
    }
    public List<String> create(Long date, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(selectedSessionServiceLine);
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "date_" + date + "_" + i;
            createBasicClaimPart(fileName, invoicesChunk);
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
        this.invoiceRequest = invoiceRequest;
    }

    private void createBasicClaimPart(String filename , List<SelectedSessionServiceLine> invoicesChunk) throws IOException, IllegalAccessException {
        createCMSPdfDocumentResourceUseCase.createResource(filename);
        notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
        serviceLineCMSDocumentFiller.create(invoicesChunk, createCMSPdfDocumentResourceUseCase.getForm());
        physicianCMSDocumentFiller.create(ProviderModelFinder.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
        locationCMSDocumentFiller.create(ClinicModelFinder.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
        createCMSPdfDocumentResourceUseCase.lockForm();
        createCMSPdfDocumentResourceUseCase.closeResource();
    }
}
