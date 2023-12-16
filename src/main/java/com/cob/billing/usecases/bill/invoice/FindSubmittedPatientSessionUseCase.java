package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.bill.posting.ClientPostingPayments;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.response.ClientPostingPaymentResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class FindSubmittedPatientSessionUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public ClientPostingPaymentResponse findClient(Pageable paging, Long clientId) {
        Page<PatientEntity> pages = patientRepository.findBySessionSubmittedByPatient(paging, clientId);
        long total = (pages).getTotalElements();
        List<ClientPostingPayments> records = pages.stream()
                .map(patientEntity -> {
                    ClientPostingPayments clientPostingPayments = new ClientPostingPayments();
                    Patient patient = mapper.map(patientEntity, Patient.class);
                    clientPostingPayments.setClientId(patient.getId());
                    clientPostingPayments.setPaymentServiceLines(constructServiceLines(patient.getSessions()));

                    return clientPostingPayments;
                })
                .collect(Collectors.toList());

        return ClientPostingPaymentResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();

    }

    public void findInsuranceCompany(Long insuranceCompanyId) {

    }

    private List<PaymentServiceLine> constructServiceLines(List<PatientSession> sessions) {
        List<PaymentServiceLine> paymentServiceLines = new ArrayList<>();
        sessions.forEach(patientSession -> {
            patientSession.getServiceCodes().forEach(serviceLine -> {
                paymentServiceLines.add(PaymentServiceLine.builder()
                        .sessionId(patientSession.getId())
                        .ServiceCodeId(serviceLine.getId())
                        .dateOfService(patientSession.getServiceDate())
                        .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                        .provider(patientSession.getDoctorInfo().getDoctorLastName() + "," + patientSession.getDoctorInfo().getDoctorFirstName())
                        .billedValue(serviceLine.getCptCode().getCharge())
                        .build());
            });
        });
        return paymentServiceLines;
    }
}
