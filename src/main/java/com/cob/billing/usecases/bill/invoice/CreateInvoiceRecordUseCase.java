package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceInsuranceCompanyInformation;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceDetailsRepository;
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
import java.util.Map;
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
    private PatientInvoiceDetailsRepository patientInvoiceDetailsRepository;
    @Autowired
    ModelMapper mapper;

    public void createRecord(InvoiceRequest invoiceRequest) {
        List<Long> createdRecordsId = new ArrayList<>();
        Map<String, List<SelectedSessionServiceLine>> dd = invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId().getDoctorInfo().getDoctorNPI()));
        PatientEntity patient = patientRepository.findById(invoiceRequest.getPatientInformation().getId()).get();
        //create multiple invoice submission in case of sessions with diff provider
        if (dd.size() > 1) {
            for (String npi : dd.keySet()) {
                PatientInvoiceEntity createdPatientInvoice = createPatientInvoice(patient,
                        invoiceRequest.getInvoiceRequestConfiguration(),
                        invoiceRequest.getInvoiceInsuranceCompanyInformation(),
                        invoiceRequest.getSubmissionType());
                createdRecordsId.add(createdPatientInvoice.getId());
                List<SelectedSessionServiceLine> serviceLines = dd.get(npi);
                List<PatientInvoiceDetailsEntity> detailsEntities = new ArrayList<>();
                serviceLines.forEach(serviceLine -> {
                    detailsEntities.add(createPatientInvoiceDetails(serviceLine.getSessionId(), serviceLine.getServiceLine(), createdPatientInvoice));
                });
                patientInvoiceDetailsRepository.saveAll(detailsEntities);
            }
        } else {
            PatientInvoiceEntity createdPatientInvoice = createPatientInvoice(patient
                    , invoiceRequest.getInvoiceRequestConfiguration()
                    , invoiceRequest.getInvoiceInsuranceCompanyInformation()
                    , invoiceRequest.getSubmissionType());
            createdRecordsId.add(createdPatientInvoice.getId());
            List<PatientInvoiceDetailsEntity> detailsEntities = new ArrayList<>();
            invoiceRequest.getSelectedSessionServiceLine().forEach(serviceLine -> {
                detailsEntities.add(createPatientInvoiceDetails(serviceLine.getSessionId(), serviceLine.getServiceLine(), createdPatientInvoice));
            });
            patientInvoiceDetailsRepository.saveAll(detailsEntities);
        }
        invoiceRequest.setRecords(createdRecordsId);
    }

    private PatientInvoiceEntity createPatientInvoice(PatientEntity patient,
                                                      InvoiceRequestConfiguration invoiceRequestConfiguration,
                                                      InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation,
                                                      SubmissionType submissionType) {
        PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
        patientInvoice.setPatient(patient);
        patientInvoice.setDelayedReason(invoiceRequestConfiguration.getDelayedReason());
        patientInvoice.setIsOneDateServicePerClaim(invoiceRequestConfiguration.getIsOneDateServicePerClaim());
        patientInvoice.setInsuranceCompany(invoiceInsuranceCompanyInformation);
        Random rand = new Random();
        String submissionId = String.format("%04d", rand.nextInt(10000));
        patientInvoice.setSubmissionId(Long.parseLong(submissionId));
        patientInvoice.setSubmissionStatus(SubmissionStatus.Success);
        patientInvoice.setSubmissionType(submissionType);
        return patientInvoiceRepository.save(patientInvoice);
    }

    private PatientInvoiceDetailsEntity createPatientInvoiceDetails(PatientSession patientSession, ServiceLine serviceLine, PatientInvoiceEntity createdPatientInvoice) {
        PatientInvoiceDetailsEntity patientInvoiceDetails = new PatientInvoiceDetailsEntity();
        patientInvoiceDetails.setPatientSession(mapper.map(patientSession, PatientSessionEntity.class));
        patientInvoiceDetails.setServiceLine(mapper.map(serviceLine, PatientSessionServiceLineEntity.class));
        patientInvoiceDetails.setPatientInvoice(createdPatientInvoice);
        return patientInvoiceDetails;
    }
}
