package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.BatchServiceLinePayment;
import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.model.bill.posting.paymnet.BatchSessionServiceLinePayment;
import com.cob.billing.model.response.SessionServiceLinePaymentResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceDetailsRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.posting.ConstructBatchServiceLinesPaymentsUseCase;
import com.cob.billing.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public SessionServiceLinePaymentResponse find(int offset, int limit, Long clientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatient(clientId);
        List<BatchSessionServiceLinePayment> response = constructBatchServiceLinesPaymentsUseCase.construct(patientSessionEntities);
        List<BatchSessionServiceLinePayment> records = PaginationUtil.paginate(response, offset, limit);
        return SessionServiceLinePaymentResponse.builder()
                .number_of_records(response.size())
                .number_of_matching_records((int) records.size())
                .records(response)
                .build();
    }

    public SessionServiceLinePaymentResponse find(int offset, int limit, PostingSearchCriteria postingSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatientFiltered(postingSearchCriteria.getEntityId(), postingSearchCriteria.getStartDate(), postingSearchCriteria.getEndDate());
        List<BatchSessionServiceLinePayment> response = constructBatchServiceLinesPaymentsUseCase.construct(patientSessionEntities);
        response.sort(Comparator.comparingLong(BatchSessionServiceLinePayment::getDos));
        List<BatchSessionServiceLinePayment> records = PaginationUtil.paginate(response, offset, limit);
        return SessionServiceLinePaymentResponse.builder()
                .number_of_records(response.size())
                .number_of_matching_records((int) records.size())
                .records(records)
                .build();
    }

    public Map<String, List<BatchSessionServiceLinePayment>> findInsuranceCompany(Long insuranceCompanyId) {
        Map<String, List<BatchSessionServiceLinePayment>> paymentServiceLinePatientMap = new HashMap<>();
        patientInvoiceDetailsRepository.findBySessionSubmittedByInsuranceCompany(insuranceCompanyId).stream()
                .distinct()
                .forEach(patientInvoiceDetails -> {
                    String patient = patientInvoiceDetails.getPatientInvoice().getPatient().getLastName() + ","
                            + patientInvoiceDetails.getPatientInvoice().getPatient().getFirstName() + ","
                            + patientInvoiceDetails.getPatientInvoice().getPatient().getId();
                    if (paymentServiceLinePatientMap.get(patient) == null) {
                        List<BatchSessionServiceLinePayment> records = new ArrayList<>();
                        records.addAll(constructBatchServiceLinesPaymentsUseCase.construct(patientInvoiceDetails.getPatientSession(), patientInvoiceDetails.getServiceLine()));
                        paymentServiceLinePatientMap.put(patient, records);
                    } else {
                        List<BatchSessionServiceLinePayment> records = paymentServiceLinePatientMap.get(patient);
                        records.addAll(constructBatchServiceLinesPaymentsUseCase.construct(patientInvoiceDetails.getPatientSession(), patientInvoiceDetails.getServiceLine()));
                    }
                });

        return paymentServiceLinePatientMap;
    }


    private BatchServiceLinePayment constructServiceLine(PatientSessionServiceLineEntity serviceLine, PatientSessionEntity patientSession) {
        return BatchServiceLinePayment.builder()
                .sessionId(patientSession.getId())
                .serviceLineId(serviceLine.getId())
                .dateOfService(patientSession.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .previousPayments(0.0)
                .balance(serviceLine.getCptCode().getCharge())
                .provider(patientSession.getDoctorInfo().getDoctorLastName() + ',' + patientSession.getDoctorInfo().getDoctorFirstName())
                .build();
    }

}
