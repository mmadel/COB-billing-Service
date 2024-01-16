package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.usecases.bill.invoice.cms.creators.PhysicianCMSDocumentCreator;
import com.cob.billing.usecases.bill.invoice.cms.creators.ServiceLineCMSDocumentCreator;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TestUseCase {

    @Autowired
    CreateCMSPdfDocumentResourceUseCase createCMSPdfDocumentResourceUseCase;
    @Autowired
    FillNonRepeatablePart fillNonRepeatablePart;
    @Autowired
    ServiceLineCMSDocumentCreator serviceLineCMSDocumentCreator;
    @Autowired
    PhysicianCMSDocumentCreator physicianCMSDocumentCreator;


    public List<String> test(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Map<DoctorInfo, List<PatientInvoiceEntity>> providersGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getDoctorInfo()));

        Map<String, List<PatientInvoiceEntity>> casesGroup =
                patientInvoiceRecords.stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getPatientSession().getCaseTitle()));


        if (providersGroup.size() > 1) {
            for (Map.Entry<DoctorInfo, List<PatientInvoiceEntity>> entry : providersGroup.entrySet()) {
                String fileName = "provider_" + entry.getKey().getDoctorNPI();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                fillNonRepeatablePart.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentCreator.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                physicianCMSDocumentCreator.create(entry.getKey(), createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        if (casesGroup.size() > 1) {
            for (Map.Entry<String, List<PatientInvoiceEntity>> entry : casesGroup.entrySet()) {
                String fileName = "case_" + entry.getKey();
                createCMSPdfDocumentResourceUseCase.createResource(fileName);
                fillNonRepeatablePart.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
                serviceLineCMSDocumentCreator.create(entry.getValue(), createCMSPdfDocumentResourceUseCase.getForm());
                DoctorInfo doctorInfo = patientInvoiceRecords.stream()
                        .findFirst()
                        .get()
                        .getPatientSession().getDoctorInfo();
                physicianCMSDocumentCreator.create(doctorInfo, createCMSPdfDocumentResourceUseCase.getForm());
                createCMSPdfDocumentResourceUseCase.lockForm();
                createCMSPdfDocumentResourceUseCase.closeResource();
                fileNames.add(fileName);
            }
        }
        if (!(providersGroup.size() > 1) && !(casesGroup.size() > 1)) {
            String fileName = "claim.pdf";
            createCMSPdfDocumentResourceUseCase.createResource(fileName);
            fillNonRepeatablePart.fill(invoiceRequest, createCMSPdfDocumentResourceUseCase.getForm());
            serviceLineCMSDocumentCreator.create(patientInvoiceRecords, createCMSPdfDocumentResourceUseCase.getForm());
            DoctorInfo doctorInfo = patientInvoiceRecords.stream()
                    .findFirst()
                    .get()
                    .getPatientSession().getDoctorInfo();
            physicianCMSDocumentCreator.create(doctorInfo, createCMSPdfDocumentResourceUseCase.getForm());
            createCMSPdfDocumentResourceUseCase.lockForm();
            createCMSPdfDocumentResourceUseCase.closeResource();
            fileNames.add(fileName);
        }
        return fileNames;
    }
}
