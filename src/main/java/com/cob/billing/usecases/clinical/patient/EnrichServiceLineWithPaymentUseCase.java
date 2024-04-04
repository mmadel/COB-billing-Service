package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePayment;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class EnrichServiceLineWithPaymentUseCase {
    @Autowired
    private FindSessionPaymentUseCase findSessionPaymentUseCase;
    public void enrichPayment(PatientSession patientSession){
        List<Long> serviceLinesIds = patientSession.getServiceCodes().stream()
                .map(ServiceLine::getId)
                .collect(Collectors.toList());
        List<ServiceLinePayment> serviceLinePayments = findSessionPaymentUseCase.findByServiceLines(serviceLinesIds);
        patientSession.getServiceCodes().stream()
                .forEach(serviceLine -> {
                    serviceLine.setPayments(calculateServiceLinePayment(serviceLinePayments, serviceLine.getId()));
                });
    }
    private double calculateServiceLinePayment(List<ServiceLinePayment> serviceLinePayments, Long serviceLineId) {
        AtomicReference<Double> payments = new AtomicReference<>((double) 0);
        serviceLinePayments.forEach(serviceLinePayment -> {
            if (serviceLinePayment.getServiceLineId().equals(serviceLineId)) {
                payments.set(payments.get() + serviceLinePayment.getPayment());
            }
        });
        return payments.get();
    }
}
