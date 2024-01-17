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
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimsDateCreator {
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

    private Map<Long, List<PatientInvoiceEntity>> dates;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if (isMultiple()) {
            for (Map.Entry<Long, List<PatientInvoiceEntity>> entry : dates.entrySet()) {
                String fileName = "date_" + entry.getKey();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
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
        dates = patientInvoiceRecords.stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getServiceDate()));
    }

    private boolean isMultiple() {
        return dates.size() > 1 ? true : false;
    }
}
