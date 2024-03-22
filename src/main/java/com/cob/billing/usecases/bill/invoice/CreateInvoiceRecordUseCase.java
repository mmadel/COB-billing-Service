package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateInvoiceRecordUseCase {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;

    @Autowired
    ModelMapper mapper;

    public List<PatientInvoiceEntity> createRecord(InvoiceRequest invoiceRequest) {
        List<PatientInvoiceEntity> toBeCreated = new ArrayList<>();
        PatientEntity patient = patientRepository.findById(invoiceRequest.getPatientInformation().getId()).get();
        Random rand = new Random();
        String submissionId = String.format("%04d", rand.nextInt(10000));
        invoiceRequest.getSelectedSessionServiceLine()
                .forEach(serviceLine -> {
                    PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
                    patientInvoice.setPatient(patient);
                    patientInvoice.setDelayedReason(invoiceRequest.getInvoiceRequestConfiguration().getDelayedReason());
                    patientInvoice.setIsOneDateServicePerClaim(invoiceRequest.getInvoiceRequestConfiguration().getIsOneDateServicePerClaim());
                    patientInvoice.setServiceLine(mapper.map(serviceLine.getServiceLine(), PatientSessionServiceLineEntity.class));
                    patientInvoice.setPatientSession(mapper.map(serviceLine.getSessionId(), PatientSessionEntity.class));
                    patientInvoice.setInsuranceCompanyId(invoiceRequest.getInvoiceInsuranceCompanyInformation().getId());
                    patientInvoice.setSubmissionId(Long.parseLong(submissionId));
                    toBeCreated.add(patientInvoice);
                });
        List<PatientInvoiceEntity> patientInvoiceRecords = StreamSupport.stream(patientInvoiceRepository.saveAll(toBeCreated).spliterator(), false)
                .collect(Collectors.toList());
        return patientInvoiceRecords;
    }

}
