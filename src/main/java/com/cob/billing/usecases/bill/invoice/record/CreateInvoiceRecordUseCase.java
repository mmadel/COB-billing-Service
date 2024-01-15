package com.cob.billing.usecases.bill.invoice.record;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateInvoiceRecordUseCase {
    @Autowired
    private ServiceLineRepository serviceLineRepository;
    @Autowired
    private PatientSessionRepository patientSessionRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;

    public List<PatientInvoiceEntity> patientInvoiceRecords;

    public void createRecord(List<SelectedSessionServiceLine> selectedSessionServiceLines
            , InvoiceRequestConfiguration invoiceRequestConfiguration
            , Long patientId) {
        List<PatientInvoiceEntity> toBeCreated = new ArrayList<>();
        PatientEntity patient = patientRepository.findById(patientId).get();
        selectedSessionServiceLines.stream()
                .forEach(serviceLine -> {
                    PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
                    patientInvoice.setPatient(patient);
                    patientInvoice.setDelayedReason(invoiceRequestConfiguration.getDelayedReason());
                    patientInvoice.setIsOneDateServicePerClaim(invoiceRequestConfiguration.getIsOneDateServicePerClaim());
                    patientInvoice.setServiceLine(serviceLineRepository.findById(serviceLine.getServiceLine()).get());
                    patientInvoice.setPatientSession(patientSessionRepository.findById(serviceLine.getSessionId()).get());
                    toBeCreated.add(patientInvoice);
                });
        patientInvoiceRecords = StreamSupport.stream(patientInvoiceRepository.saveAll(toBeCreated).spliterator(), false)
                .collect(Collectors.toList());

    }
}
