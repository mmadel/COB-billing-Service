package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.bill.payment.PatientPaymentServiceLineEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.enums.SessionAction;
import com.cob.billing.model.bill.posting.PaymentServiceLine;
import com.cob.billing.repositories.bill.posting.PatientPaymentServiceLineRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateServiceLinesPaymentUseCase {
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientPaymentServiceLineRepository patientPaymentServiceLineRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public void create(List<PaymentServiceLine> payments, Long patientId) {
        PatientEntity patient = patientRepository.findById(patientId).get();
        List<PatientPaymentServiceLineEntity> serviceLineEntities = payments.stream()
                .map(paymentServiceLine -> {
                    PatientPaymentServiceLineEntity toBeCreated = mapper.map(paymentServiceLine, PatientPaymentServiceLineEntity.class);
                    toBeCreated.setPatient(patient);
                    return toBeCreated;
                })
                .collect(Collectors.toList());
        patientPaymentServiceLineRepository.saveAll(serviceLineEntities);

        updateSessionAndServiceLineStatus(payments);
    }

    public void  create(Map<Long, List<PaymentServiceLine>> payments){
        payments.forEach((patientId, paymentsServiceLines) -> {
            create(paymentsServiceLines,patientId);
        });
    }

    private void updateSessionAndServiceLineStatus(List<PaymentServiceLine> payments) {
        Map<Long, List<PaymentServiceLine>> groupedSessions = payments.stream()
                .collect(Collectors.groupingBy(PaymentServiceLine::getSessionId));
        List<Long> preparedSessionIds = new ArrayList<>();
        List<Long> partialSessionIds = new ArrayList<>();
        groupedSessions.entrySet().stream()
                .forEach(sessionGroup -> {
                    PatientSessionStatus status = evaluateServiceLineStatusAndGetSessionStatus(sessionGroup.getValue());
                    switch (status) {
                        case Prepare:
                            preparedSessionIds.add(sessionGroup.getKey());
                            break;
                        case Partial:
                            partialSessionIds.add(sessionGroup.getKey());
                            break;
                    }
                });
        if (!preparedSessionIds.isEmpty())
            chaneSessionStatus(preparedSessionIds, PatientSessionStatus.Prepare);
        if (!partialSessionIds.isEmpty())
            chaneSessionStatus(partialSessionIds, PatientSessionStatus.Partial);
    }

    private PatientSessionStatus evaluateServiceLineStatusAndGetSessionStatus(List<PaymentServiceLine> paymentServiceLines) {
        AtomicReference<Boolean> resubmitFlag = new AtomicReference<>(false);
        AtomicReference<Boolean> closeFlag = new AtomicReference<>(false);
        List<Long> resubmittedServiceLines = new ArrayList<>();
        List<Long> closedServiceLines = new ArrayList<>();
        paymentServiceLines.stream()
                .forEach(paymentServiceLine -> {
                    if (paymentServiceLine.getSessionAction() != null) {
                        if (paymentServiceLine.getSessionAction().equals(SessionAction.Resubmit)) {
                            resubmittedServiceLines.add(paymentServiceLine.getServiceCodeId());
                            resubmitFlag.set(true);
                        }
                        if (paymentServiceLine.getSessionAction().equals(SessionAction.Close)) {
                            closedServiceLines.add(paymentServiceLine.getServiceCodeId());
                            closeFlag.set(true);
                        }
                    }
                });
        if (!resubmittedServiceLines.isEmpty())
            updateServiceLineStatus(resubmittedServiceLines, "Initial");
        if (!closedServiceLines.isEmpty())
            updateServiceLineStatus(closedServiceLines, "Close");

        return checkSessionStatusFlag(resubmitFlag.get().booleanValue(), closeFlag.get().booleanValue());
    }

    private void updateServiceLineStatus(List<Long> serviceLines, String type) {
        List<PatientSessionServiceLineEntity> result =
                StreamSupport.stream(serviceLineRepository.findAllById(serviceLines).spliterator(), false)
                        .collect(Collectors.toList());
        result.stream()
                .forEach(serviceLine -> serviceLine.setType(type));

        serviceLineRepository.saveAll(result);
    }

    private void chaneSessionStatus(List<Long> sessionId, PatientSessionStatus status) {
        List<PatientSessionEntity> result =
                StreamSupport.stream(patientSessionRepository.findAllById(sessionId).spliterator(), false)
                        .collect(Collectors.toList());
        result.stream()
                .forEach(patientSession -> patientSession.setStatus(status));
        patientSessionRepository.saveAll(result);
    }

    private PatientSessionStatus checkSessionStatusFlag(Boolean preparedSessionFlag, Boolean partialSessionFlag) {
        if (preparedSessionFlag && partialSessionFlag)
            return PatientSessionStatus.Partial;
        if (preparedSessionFlag)
            return PatientSessionStatus.Prepare;
        return PatientSessionStatus.Submit;
    }
}
