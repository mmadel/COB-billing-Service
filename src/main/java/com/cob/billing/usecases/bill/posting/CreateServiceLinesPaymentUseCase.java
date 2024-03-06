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
        Map<Long, List<PaymentServiceLine>> groupedSessions = payments.stream()
                .collect(Collectors.groupingBy(PaymentServiceLine::getSessionId));
        List<Long> sessionId = new ArrayList<>();
        List<PaymentServiceLine> serviceLines = new ArrayList<>();
        groupedSessions.entrySet().stream()
                .forEach(sessionGroup -> {
                    sessionId.add(sessionGroup.getKey());
                    serviceLines.addAll(sessionGroup.getValue());
                });
        evaluateServiceLinesStatus(payments);
        evaluateSessionStatus(sessionId);
    }

    public void create(Map<Long, List<PaymentServiceLine>> payments) {
        payments.forEach((patientId, paymentsServiceLines) -> {
            create(paymentsServiceLines, patientId);
        });
    }

    private void evaluateServiceLinesStatus(List<PaymentServiceLine> payments) {
        List<Long> resubmittedServiceLines = new ArrayList<>();
        List<Long> closedServiceLines = new ArrayList<>();
        payments.forEach(paymentServiceLine -> {
            if (paymentServiceLine.getSessionAction() != null) {
                if (paymentServiceLine.getSessionAction().equals(SessionAction.Resubmit)) {
                    resubmittedServiceLines.add(paymentServiceLine.getServiceCodeId());
                }
                if (paymentServiceLine.getSessionAction().equals(SessionAction.Close)) {
                    closedServiceLines.add(paymentServiceLine.getServiceCodeId());
                }
            }
        });
        if (!resubmittedServiceLines.isEmpty())
            updateServiceLineStatus(resubmittedServiceLines, "Initial");
        if (!closedServiceLines.isEmpty())
            updateServiceLineStatus(closedServiceLines, "Close");
    }

    private void evaluateSessionStatus(List<Long> sessions) {
        List<PatientSessionEntity> submitSessions = new ArrayList<>();
        List<PatientSessionEntity> partialSessions = new ArrayList<>();
        patientSessionRepository.findAllById(sessions).forEach(patientSessionEntity -> {
            if(patientSessionEntity.getServiceCodes().stream().allMatch(patientSessionServiceLineEntity -> patientSessionServiceLineEntity.getType().equals("Close"))){
                patientSessionEntity.setStatus(PatientSessionStatus.Submit);
                submitSessions.add(patientSessionEntity);
            }else{
                patientSessionEntity.setStatus(PatientSessionStatus.Partial);
                partialSessions.add(patientSessionEntity);
            }
        });
        if(submitSessions.size()> 0)
            patientSessionRepository.saveAll(submitSessions);
        if(partialSessions.size()> 0)
            patientSessionRepository.saveAll(partialSessions);
    }

    private void updateServiceLineStatus(List<Long> serviceLines, String type) {
        List<PatientSessionServiceLineEntity> result =
                StreamSupport.stream(serviceLineRepository.findAllById(serviceLines).spliterator(), false)
                        .collect(Collectors.toList());
        result.stream()
                .forEach(serviceLine -> serviceLine.setType(type));

        serviceLineRepository.saveAll(result);
    }
}
