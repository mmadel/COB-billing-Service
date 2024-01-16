package com.cob.billing.usecases.bill.invoice.cms.filler;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSPdfDocumentResourceUseCase;
import com.cob.billing.usecases.bill.invoice.cms.filler.LocationCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.NotRepeatableCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.PhysicianCMSDocumentFiller;
import com.cob.billing.usecases.bill.invoice.cms.filler.ServiceLineCMSDocumentFiller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FillCMSDocumentUseCase {

    @Autowired
    CreateCMSPdfDocumentResourceUseCase createCMSPdfDocumentResourceUseCase;
    @Autowired
    NotRepeatableCMSDocumentFiller notRepeatableCMSDocumentFiller;
    @Autowired
    ServiceLineCMSDocumentFiller serviceLineCMSDocumentFiller;
    @Autowired
    PhysicianCMSDocumentFiller physicianCMSDocumentFiller;
    @Autowired
    LocationCMSDocumentFiller locationCMSDocumentFiller;
    @Autowired
    ModelMapper mapper;


    public List<String> fill(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Map<DoctorInfo, List<PatientInvoiceEntity>> providersGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getDoctorInfo()));

        Map<String, List<PatientInvoiceEntity>> casesGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getCaseTitle()));

        Map<ClinicEntity, List<PatientInvoiceEntity>> clinicsGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getClinic()));

        Map<Long, List<PatientInvoiceEntity>> datesGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getServiceDate()));


        if (providersGroup.size() > 1) {
            for (Map.Entry<DoctorInfo, List<PatientInvoiceEntity>> entry : providersGroup.entrySet()) {
                String fileName = "provider_" + entry.getKey().getDoctorNPI();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(entry.getKey(), createCMSPdfDocumentResourceUseCase.getForm());
                locationCMSDocumentFiller.create(getClinic(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        if (casesGroup.size() > 1) {
            for (Map.Entry<String, List<PatientInvoiceEntity>> entry : casesGroup.entrySet()) {
                String fileName = "case_" + entry.getKey();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(getDoctorInfo(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                locationCMSDocumentFiller.create(getClinic(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        if (clinicsGroup.size() > 1) {
            for (Map.Entry<ClinicEntity, List<PatientInvoiceEntity>> entry : clinicsGroup.entrySet()) {
                String fileName = "clinic_" + entry.getKey().getNpi();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentFiller.create(getDoctorInfo(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                Clinic clinic = mapper.map(entry.getKey(), Clinic.class);
                locationCMSDocumentFiller.create(clinic, createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }

        }
        if (!(providersGroup.size() > 1) && !(casesGroup.size() > 1) && !(clinicsGroup.size() > 1)) {
            String fileName = "claim.pdf";
            createCMSPdfDocumentResourceUseCase.createResource(fileName);
            notRepeatableCMSDocumentFiller.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
            serviceLineCMSDocumentFiller.create(patientInvoiceRecords, createCMSPdfDocumentResourceUseCase.getForm());
            physicianCMSDocumentFiller.create(getDoctorInfo(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
            locationCMSDocumentFiller.create(getClinic(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
            createCMSPdfDocumentResourceUseCase.lockForm();
            createCMSPdfDocumentResourceUseCase.closeResource();
            fileNames.add(fileName);

        }
        if ((!(providersGroup.size() > 1) && !(casesGroup.size() > 1) && !(clinicsGroup.size() > 1))
                && (invoiceRequest.getInvoiceRequestConfiguration().getIsOneDateServicePerClaim() != null
                && invoiceRequest.getInvoiceRequestConfiguration().getIsOneDateServicePerClaim())) {
            for (Map.Entry<Long, List<PatientInvoiceEntity>> entry : datesGroup.entrySet()) {
                String fileName = "date_" + entry.getKey();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                serviceLineCMSDocumentFiller.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());

                physicianCMSDocumentFiller.create(getDoctorInfo(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());

                locationCMSDocumentFiller.create(getClinic(patientInvoiceRecords), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
            }
        }
        return fileNames;
    }

    private DoctorInfo getDoctorInfo(List<PatientInvoiceEntity> patientInvoiceRecords) {
        return patientInvoiceRecords.stream()
                .findFirst()
                .get()
                .getPatientSession().getDoctorInfo();
    }

    private Clinic getClinic(List<PatientInvoiceEntity> patientInvoiceRecords) {
        return mapper.map(patientInvoiceRecords.stream()
                .findFirst()
                .get()
                .getPatientSession().getClinic(), Clinic.class);
    }
}
