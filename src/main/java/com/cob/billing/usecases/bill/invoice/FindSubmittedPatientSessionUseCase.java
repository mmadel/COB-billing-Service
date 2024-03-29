package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FindSubmittedPatientSessionUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;

    public List<PaymentServiceLine> findClient(Long clientId) {
        List<Object> objs = patientRepository.findBySessionSubmittedByPatient(clientId);
        List<PaymentServiceLine> records = new ArrayList<>();
        objs.stream().forEach(o -> {
            Object[] result = (Object[]) o;
            PatientSessionServiceLineEntity serviceLine = (PatientSessionServiceLineEntity) result[0];
            PatientSessionEntity session = (PatientSessionEntity) result[1];
            records.add(constructServiceLine(serviceLine, session));
        });
        return records;
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
