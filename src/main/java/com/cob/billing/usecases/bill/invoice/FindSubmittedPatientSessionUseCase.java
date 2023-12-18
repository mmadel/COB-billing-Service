package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindSubmittedPatientSessionUseCase {
    @Autowired
    PatientRepository patientRepository;
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

    public void findInsuranceCompany(Long insuranceCompanyId) {

    }

    private PaymentServiceLine constructServiceLine(PatientSessionServiceLineEntity serviceLine, PatientSessionEntity session) {
        return PaymentServiceLine.builder()
                .sessionId(session.getId())
                .ServiceCodeId(serviceLine.getId())
                .dateOfService(session.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .previousPayments(0.0)
                .payment(0.0)
                .adjust(0.0)
                .balance(serviceLine.getCptCode().getCharge())
                .provider(session.getDoctorInfo().getDoctorLastName() + "," + session.getDoctorInfo().getDoctorFirstName())
                .build();
    }
}
