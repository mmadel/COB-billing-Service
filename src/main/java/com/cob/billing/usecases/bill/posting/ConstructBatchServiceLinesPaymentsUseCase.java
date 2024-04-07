package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.paymnet.BatchSessionServiceLinePayment;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConstructBatchServiceLinesPaymentsUseCase {
    @Autowired
    FindSessionPaymentUseCase findSessionPaymentUseCase;

    public List<BatchSessionServiceLinePayment> construct(List<PatientSessionEntity> sessions) {
        List<BatchSessionServiceLinePayment> result = new ArrayList<>();
        Map<PatientSessionEntity, List<PatientSessionServiceLineEntity>> sessionMap = sessions.stream()
                .collect(Collectors.toMap(
                        session -> session,
                        session -> session.getServiceCodes()
                ));
        for (PatientSessionEntity key : sessionMap.keySet()) {
            List<SessionServiceLinePayment> payments = findSessionPaymentUseCase.find(sessionMap.get(key).stream()
                    .map(PatientSessionServiceLineEntity::getId)
                    .collect(Collectors.toList()));
            sessionMap.get(key).forEach(serviceLine -> {
                if (!payments.isEmpty()) {
                    SessionServiceLinePayment payment = findMatchPayment(payments, serviceLine.getId());
                    if (payment != null)
                        result.add(buildServiceLinePayment(key, serviceLine, payment));
                    else
                        result.add(buildServiceLinePayment(key, serviceLine, null));
                } else
                    result.add(buildServiceLinePayment(key, serviceLine));
            });
        }
        return result;
    }
    public List<BatchSessionServiceLinePayment> construct(PatientSessionEntity session , PatientSessionServiceLineEntity serviceLine) {
        List<SessionServiceLinePayment> payments = findSessionPaymentUseCase.find(Arrays.asList(serviceLine.getId()));
        List<BatchSessionServiceLinePayment> result = new ArrayList<>();
        if (!payments.isEmpty()) {
            result.add(buildServiceLinePayment(session, serviceLine, payments.stream().findFirst().get()));
        }else{
            result.add(buildServiceLinePayment(session, serviceLine,null));
        }
        return result;
    }

    private BatchSessionServiceLinePayment buildServiceLinePayment(PatientSessionEntity patientSession,
                                                                   PatientSessionServiceLineEntity serviceLine,
                                                                   SessionServiceLinePayment payment) {
        BatchSessionServiceLinePayment sessionServiceLinePayment = new BatchSessionServiceLinePayment();
        sessionServiceLinePayment.setDos(patientSession.getServiceDate());
        sessionServiceLinePayment.setCharge(serviceLine.getCptCode().getCharge());
        sessionServiceLinePayment.setProvider(patientSession.getDoctorInfo().getDoctorLastName() + ',' + patientSession.getDoctorInfo().getDoctorFirstName());
        sessionServiceLinePayment.setCpt(serviceLine.getCptCode().getServiceCode() + '.' + serviceLine.getCptCode().getModifier());
        sessionServiceLinePayment.setServiceLineId(serviceLine.getId());
        sessionServiceLinePayment.setPreviousPayment(payment != null ? payment.getPayment() : 0.0);
        sessionServiceLinePayment.setBalance(payment != null ? payment.getBalance() : serviceLine.getCptCode().getCharge());
        return sessionServiceLinePayment;
    }

    private BatchSessionServiceLinePayment buildServiceLinePayment(PatientSessionEntity patientSession,
                                                                   PatientSessionServiceLineEntity serviceLine) {
        BatchSessionServiceLinePayment sessionServiceLinePayment = new BatchSessionServiceLinePayment();
        sessionServiceLinePayment.setDos(patientSession.getServiceDate());
        sessionServiceLinePayment.setProvider(patientSession.getDoctorInfo().getDoctorLastName() + ',' + patientSession.getDoctorInfo().getDoctorFirstName());
        sessionServiceLinePayment.setCpt(serviceLine.getCptCode().getServiceCode() + '.' + serviceLine.getCptCode().getModifier());
        sessionServiceLinePayment.setPreviousPayment(0.0);
        sessionServiceLinePayment.setBalance(0.0);
        sessionServiceLinePayment.setServiceLineId(serviceLine.getId());

        return sessionServiceLinePayment;
    }

    private SessionServiceLinePayment findMatchPayment(List<SessionServiceLinePayment> payments, Long serviceLineId) {
        SessionServiceLinePayment matchPayment = null;
        for (SessionServiceLinePayment payment : payments) {
            if (payment.getServiceLineId().equals(serviceLineId)) {
                matchPayment = payment;
                break;
            }
        }
        return matchPayment;
    }
}
