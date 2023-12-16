package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.ServiceLineEntity;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.response.ClientPostingPaymentResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class FindSubmittedPatientSessionUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public ClientPostingPaymentResponse findClient(Pageable paging, Long clientId) {
        Page<Object> pages = patientRepository.findBySessionSubmittedByPatient(paging, clientId);
        long total = (pages).getTotalElements();
        List<PaymentServiceLine> records = new ArrayList<>();
        pages.getContent().stream().forEach(o -> {
            Object[] result = (Object[]) o;
            ServiceLineEntity serviceLine = (ServiceLineEntity) result[0];
            PatientSessionEntity session = (PatientSessionEntity) result[1];
            records.add(constructServiceLine(serviceLine, session));
        });

        return ClientPostingPaymentResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    public void findInsuranceCompany(Long insuranceCompanyId) {

    }

    private PaymentServiceLine constructServiceLine(ServiceLineEntity serviceLine, PatientSessionEntity session) {
        return PaymentServiceLine.builder()
                .sessionId(session.getId())
                .ServiceCodeId(serviceLine.getId())
                .dateOfService(session.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .provider(session.getDoctorInfo().getDoctorLastName() + "," + session.getDoctorInfo().getDoctorFirstName())
                .build();
    }
}
