package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.finder.ClinicModelFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimsProviderCreator {
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

    private Map<DoctorInfo, List<PatientInvoiceEntity>> providers;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if(isMultiple()){
            for (Map.Entry<DoctorInfo, List<PatientInvoiceEntity>> entry : providers.entrySet()) {
                String fileName = "provider_" + entry.getKey().getDoctorNPI();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(entry.getKey(), createCMSPdfDocumentResourceUseCase.getForm());
                locationCMSDocumentFiller.create(ClinicModelFinder.find(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    private void group(List<PatientInvoiceEntity> patientInvoiceRecords) {
        providers =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getDoctorInfo()));
    }

    private boolean isMultiple() {
        return providers.size() > 1 ? true : false;
    }
}
