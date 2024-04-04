package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.BatchServiceLinePayment;
import com.cob.billing.model.bill.posting.filter.PostingSearchCriteria;
import com.cob.billing.model.response.ClientPostingPaymentResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceDetailsRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.util.PaginationUtil;
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

    public ClientPostingPaymentResponse find(int offset, int limit, Long clientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatient(clientId);
        List<BatchServiceLinePayment> response = createPaymentServiceLineResponse(patientSessionEntities);
        List<BatchServiceLinePayment> records = PaginationUtil.paginate(response, offset, limit);
        return ClientPostingPaymentResponse.builder()
                .number_of_records(response.size())
                .number_of_matching_records((int) records.size())
                .records(records)
                .build();
    }

    public ClientPostingPaymentResponse find(int offset, int limit, PostingSearchCriteria postingSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatientFiltered(postingSearchCriteria.getEntityId(), postingSearchCriteria.getStartDate(), postingSearchCriteria.getEndDate());
        List<BatchServiceLinePayment> response = createPaymentServiceLineResponse(patientSessionEntities);
        List<BatchServiceLinePayment> records = PaginationUtil.paginate(response, offset, limit);
        return ClientPostingPaymentResponse.builder()
                .number_of_records(response.size())
                .number_of_matching_records((int) records.size())
                .records(records)
                .build();
    }

    public Map<String, List<BatchServiceLinePayment>> findInsuranceCompany(Long insuranceCompanyId) {
        Map<String, List<BatchServiceLinePayment>> paymentServiceLinePatientMap = new HashMap<>();

        patientInvoiceDetailsRepository.findBySessionSubmittedByInsuranceCompany(insuranceCompanyId).stream()
                .forEach(patientInvoiceDetails -> {
                    String patient = patientInvoiceDetails.getPatientInvoice().getPatient().getLastName() + ","
                            + patientInvoiceDetails.getPatientInvoice().getPatient().getFirstName() + ","
                            + patientInvoiceDetails.getPatientInvoice().getPatient().getId();
                    if (paymentServiceLinePatientMap.get(patient) == null) {
                        List<BatchServiceLinePayment> records = new ArrayList<>();
                        records.add(constructServiceLine(patientInvoiceDetails.getServiceLine(),
                                patientInvoiceDetails.getPatientSession()));
                        paymentServiceLinePatientMap.put(patient, records);
                    } else {
                        List<BatchServiceLinePayment> records = paymentServiceLinePatientMap.get(patient);
                        records.add(constructServiceLine(patientInvoiceDetails.getServiceLine(), patientInvoiceDetails.getPatientSession()));
                    }
                });

        return paymentServiceLinePatientMap;
    }

    private List<BatchServiceLinePayment> createPaymentServiceLineResponse(List<PatientSessionEntity> patientSessionEntities) {
        List<BatchServiceLinePayment> response = new ArrayList<>();
        patientSessionEntities.stream()
                .forEach(patientSessionEntity -> {
                    patientSessionEntity.getServiceCodes().stream()
                            .forEach(patientSessionServiceLineEntity -> {
                                BatchServiceLinePayment batchServiceLinePayment = constructServiceLine(patientSessionServiceLineEntity,
                                        patientSessionEntity);
                                response.add(batchServiceLinePayment);
                            });
                });
        return response;
    }

    private BatchServiceLinePayment constructServiceLine(PatientSessionServiceLineEntity serviceLine, PatientSessionEntity patientSession) {
        return BatchServiceLinePayment.builder()
                .sessionId(patientSession.getId())
                .ServiceCodeId(serviceLine.getId())
                .dateOfService(patientSession.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .previousPayments(0.0)
                .balance(serviceLine.getCptCode().getCharge())
                .provider(patientSession.getDoctorInfo().getDoctorLastName() + ',' + patientSession.getDoctorInfo().getDoctorFirstName())
                .build();
    }

}
