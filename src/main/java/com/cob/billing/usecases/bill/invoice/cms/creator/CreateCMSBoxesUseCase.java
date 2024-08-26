package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.FindClinicAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.FindProviderAssignedToServiceLinesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateCMSBoxesUseCase {
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
    public void create(InvoiceRequest invoiceRequest , String filename , List<SelectedSessionServiceLine> invoicesChunk) throws IOException, IllegalAccessException {
        createCMSPdfDocumentResourceUseCase.createResource(filename);
        notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
        serviceLineCMSDocumentFiller.create(invoicesChunk, createCMSPdfDocumentResourceUseCase.getForm());
        physicianCMSDocumentFiller.create(FindProviderAssignedToServiceLinesUseCase.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
        locationCMSDocumentFiller.create(FindClinicAssignedToServiceLinesUseCase.find(invoicesChunk), createCMSPdfDocumentResourceUseCase.getForm());
        createCMSPdfDocumentResourceUseCase.lockForm();
        createCMSPdfDocumentResourceUseCase.closeResource();
    }
}
