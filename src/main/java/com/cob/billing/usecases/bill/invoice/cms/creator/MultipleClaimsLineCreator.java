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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MultipleClaimsLineCreator {

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


    private List<List<PatientInvoiceEntity>> serviceLines;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if(isExceed(patientInvoiceRecords)){
            for (int i = 0; i < serviceLines.size(); i++) {
                String fileName = "SL_" + i;
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(serviceLines.get(i), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(ProviderModelFinder.find(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                locationCMSDocumentFiller.create(ClinicModelFinder.find(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    private void group(List<PatientInvoiceEntity> patientInvoiceRecords) {
        int chunkSize = 6;
        serviceLines = IntStream.range(0, (patientInvoiceRecords.size() + chunkSize - 1) / chunkSize)
                .mapToObj(i -> patientInvoiceRecords.subList(i * chunkSize, Math.min((i + 1) * chunkSize, patientInvoiceRecords.size())))
                .collect(Collectors.toList());
    }

    private boolean isExceed(List<PatientInvoiceEntity> patientInvoiceRecords) {
        return patientInvoiceRecords.size() > 6;
    }
}
