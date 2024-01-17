package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.finder.ClinicModelFinder;
import com.cob.billing.usecases.bill.invoice.cms.finder.ProviderModelFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FilesClaimCreator {
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

    public List<String> create(String fileName, InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> invoices, List<String> fileNames) throws IOException {
        createCMSPdfDocumentResourceUseCase.createResource(fileName);
        notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
        serviceLineCMSDocumentFiller.create(invoices, createCMSPdfDocumentResourceUseCase.getForm());
        physicianCMSDocumentFiller.create(ProviderModelFinder.find(invoices), createCMSPdfDocumentResourceUseCase.getForm());
        locationCMSDocumentFiller.create(ClinicModelFinder.find(invoices), createCMSPdfDocumentResourceUseCase.getForm());
        createCMSPdfDocumentResourceUseCase.lockForm();
        createCMSPdfDocumentResourceUseCase.closeResource();
        fileNames.add(fileName);
        return fileNames;
    }
}
