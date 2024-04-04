package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.BatchServiceLinePayment;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class CreateBatchServiceLinePaymentUseCase {
    @Autowired
    private FindSessionPaymentUseCase findSessionPaymentUseCase;

    public List<BatchServiceLinePayment> create(PatientSessionEntity patientSession) {
        List<BatchServiceLinePayment> response = new ArrayList<>();
        List<Long> serviceLinesIds = patientSession.getServiceCodes().stream()
                .map(PatientSessionServiceLineEntity::getId)
                .collect(Collectors.toList());

        List<SessionServiceLinePayment> sessionServiceLinePayments = findSessionPaymentUseCase.findByServiceLines(serviceLinesIds);
        patientSession.getServiceCodes().stream()
                .forEach(patientSessionServiceLineEntity -> {
                    BatchServiceLinePayment batchServiceLinePayment = constructBatchServiceLinePayment(patientSessionServiceLineEntity,
                            patientSession, sessionServiceLinePayments);
                    response.add(batchServiceLinePayment);
                });
        return response;
    }

    private BatchServiceLinePayment constructBatchServiceLinePayment(PatientSessionServiceLineEntity serviceLine
            , PatientSessionEntity patientSession
            , List<SessionServiceLinePayment> sessionServiceLinePayments) {
        return BatchServiceLinePayment.builder()
                .sessionId(patientSession.getId())
                .ServiceCodeId(serviceLine.getId())
                .dateOfService(patientSession.getServiceDate())
                .cpt(serviceLine.getCptCode().getServiceCode() + "." + serviceLine.getCptCode().getModifier())
                .billedValue(serviceLine.getCptCode().getCharge())
                .previousPayments(calculateServiceLinePayment(sessionServiceLinePayments, serviceLine.getId()))
                .balance(calculateServiceLineBalance(sessionServiceLinePayments, serviceLine.getId()))
                .provider(patientSession.getDoctorInfo().getDoctorLastName() + ',' + patientSession.getDoctorInfo().getDoctorFirstName())
                .build();
    }

    private double calculateServiceLinePayment(List<SessionServiceLinePayment> sessionServiceLinePayments, Long serviceLineId) {
        AtomicReference<Double> payments = new AtomicReference<>((double) 0);
        sessionServiceLinePayments.forEach(serviceLinePayment -> {
            if (serviceLinePayment.getServiceLineId().equals(serviceLineId)) {
                payments.set(payments.get() + serviceLinePayment.getPayment());
            }
        });
        return payments.get();
    }
    private double calculateServiceLineBalance(List<SessionServiceLinePayment> sessionServiceLinePayments, Long serviceLineId) {
        double balance =0;
        for(SessionServiceLinePayment sessionServiceLinePayment: sessionServiceLinePayments){
            if (sessionServiceLinePayment.getServiceLineId().equals(serviceLineId)) {
                balance = sessionServiceLinePayment.getBalance();
                break;
            }
        }
        return balance;
    }
}
