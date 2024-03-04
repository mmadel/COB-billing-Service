package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceResponse;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.model.response.ClientPostingPaymentResponse;
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

    public ClientPostingPaymentResponse find(int offset, int limit, Long clientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findSubmittedSessionsByPatient(clientId);
        List<PaymentServiceLine> response = new ArrayList<>();
        patientSessionEntities.stream()
                .forEach(patientSessionEntity -> {
                    patientSessionEntity.getServiceCodes().stream()
                            .forEach(patientSessionServiceLineEntity -> {
                                PaymentServiceLine paymentServiceLine = constructServiceLine(patientSessionServiceLineEntity, patientSessionEntity);
                                response.add(paymentServiceLine);
                            });
                });
        List<PaymentServiceLine> records = PaginationUtil.paginate(response, offset, limit);
        return ClientPostingPaymentResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) response.size())
                .records(records)
                .build();
    }

    public Map<String, List<PaymentServiceLine>> findInsuranceCompany(Long insuranceCompanyId) {
        Map<String, List<PaymentServiceLine>> paymentServiceLinePatientMap = new HashMap<>();
        patientInvoiceRepository.findBySessionSubmittedByInsuranceCompany(insuranceCompanyId).stream()
                .forEach(patientInvoice -> {
                    String patient = patientInvoice.getPatient().getLastName() + ","
                            + patientInvoice.getPatient().getFirstName() + ","
                            + patientInvoice.getPatient().getId();
                    if (paymentServiceLinePatientMap.get(patient) == null) {
                        List<PaymentServiceLine> records = new ArrayList<>();
                        records.add(constructServiceLine(patientInvoice.getServiceLine(), patientInvoice.getPatientSession()));
                        paymentServiceLinePatientMap.put(patient, records);
                    } else {
                        List<PaymentServiceLine> records = paymentServiceLinePatientMap.get(patient);
                        records.add(constructServiceLine(patientInvoice.getServiceLine(), patientInvoice.getPatientSession()));
                    }
                });

        return paymentServiceLinePatientMap;
    }

    private PaymentServiceLine constructServiceLine(PatientSessionServiceLineEntity serviceLine, PatientSessionEntity session) {
        return PaymentServiceLine.builder()
                .sessionId(session.getId())
                .ServiceCodeId(serviceLine.getId())
                .dateOfService(session.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .previousPayments(0.0)
                .balance(serviceLine.getCptCode().getCharge())
                .provider(session.getDoctorInfo().getDoctorLastName() + "," + session.getDoctorInfo().getDoctorFirstName())
                .build();
    }

}
