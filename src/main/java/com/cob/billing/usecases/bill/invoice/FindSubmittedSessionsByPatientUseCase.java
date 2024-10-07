package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.model.bill.posting.paymnet.BatchSessionServiceLinePayment;
import com.cob.billing.model.response.SessionServiceLinePaymentResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceDetailsRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.BuildSessionServiceLinePayment;
import com.cob.billing.usecases.bill.posting.ConstructBatchServiceLinesPaymentsUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FindSubmittedSessionsByPatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientInvoiceDetailsRepository patientInvoiceDetailsRepository;
    @Autowired
    ConstructBatchServiceLinesPaymentsUseCase constructBatchServiceLinesPaymentsUseCase;
    @Autowired
    BuildSessionServiceLinePayment sessionServiceLinePayment;
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;

    public SessionServiceLinePaymentResponse find(int offset, int limit, Long clientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatient(clientId);
        return sessionServiceLinePayment.build(patientSessionEntities, offset, limit);
    }

    public SessionServiceLinePaymentResponse find(int offset, int limit, PostingSearchCriteria postingSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatientFiltered(postingSearchCriteria.getEntityId(), postingSearchCriteria.getStartDate(), postingSearchCriteria.getEndDate());
        return sessionServiceLinePayment.build(patientSessionEntities, offset, limit);
    }

    public Map<String, List<BatchSessionServiceLinePayment>> findInsuranceCompany(Long insuranceCompanyId) {
        List<PatientSubmittedClaim> claims = patientSubmittedClaimRepository.findBySessionSubmittedByInsuranceCompany(insuranceCompanyId);
        Map<String, List<BatchSessionServiceLinePayment>> paymentServiceLinePatientMap = new HashMap<>();
        claims.stream()
                .distinct()
                .forEach(claim -> {
                    String patient = claim.getPatientSession().getPatient().getLastName() + ","
                            + claim.getPatientSession().getPatient().getFirstName() + ","
                            + claim.getPatientSession().getPatient().getId();

                    claim.getPatientSession().getServiceCodes().stream()
                            .forEach(serviceLine -> {
                                if (serviceLine.getType().equals("Invoice")) {
                                    if (paymentServiceLinePatientMap.get(patient) == null) {
                                        List<BatchSessionServiceLinePayment> records = new ArrayList<>();
                                        records.addAll(constructBatchServiceLinesPaymentsUseCase.construct(claim.getPatientSession(), serviceLine));
                                        paymentServiceLinePatientMap.put(patient, records);
                                    } else {
                                        List<BatchSessionServiceLinePayment> records = paymentServiceLinePatientMap.get(patient);
                                        records.addAll(constructBatchServiceLinesPaymentsUseCase.construct(claim.getPatientSession(), serviceLine));
                                    }
                                }
                            });
                });
        return paymentServiceLinePatientMap;
    }
}
