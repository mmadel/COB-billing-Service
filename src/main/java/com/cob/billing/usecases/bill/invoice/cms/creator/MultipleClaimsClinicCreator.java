package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.finder.ProviderModelFinder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimsClinicCreator {
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
    @Autowired
    ModelMapper mapper;

    Map<ClinicEntity, List<PatientInvoiceEntity>> clinics;

    public List<String> create(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        group(patientInvoiceRecords);
        if (isMultiple()) {
            for (Map.Entry<ClinicEntity, List<PatientInvoiceEntity>> entry : clinics.entrySet()) {
                String fileName = "clinic_" + entry.getKey().getNpi();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(ProviderModelFinder.find(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                Clinic clinic = mapper.map(entry.getKey(), Clinic.class);
                locationCMSDocumentFiller.create(clinic, createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    private void group(List<PatientInvoiceEntity> patientInvoiceRecords) {
        clinics =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getClinic()));
    }

    private boolean isMultiple() {
        return clinics.size() > 1 ? true : false;
    }

}
