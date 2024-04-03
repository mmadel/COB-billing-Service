package com.cob.billing.usecases.bill.posting;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePayment;
import com.cob.billing.repositories.bill.posting.PatientSessionServiceLinePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class FindSessionPaymentUseCase {
    @Autowired
    private PatientSessionServiceLinePaymentRepository patientSessionServiceLinePaymentRepository;


    public List<ServiceLinePayment> findByServiceLines(List<Long> serviceLinesIds) {
        List<ServiceLinePayment> result = new ArrayList<>();
        List<ServiceLinePayment> list =patientSessionServiceLinePaymentRepository.findByServiceLines(serviceLinesIds).get();
        Map<Long, List<ServiceLinePayment>> groupingByServiceId = list.stream()
                .collect(Collectors.groupingBy(ServiceLinePayment::getServiceLineId));
        for (Long key : groupingByServiceId.keySet()) {
            System.out.println(key + ":" + groupingByServiceId.get(key));
            List<ServiceLinePayment> groupingServiceLines =groupingByServiceId.get(key);
            if(groupingServiceLines.size()>1){
                ServiceLinePayment mostServiceLinePayment = groupingServiceLines.stream()
                        .max(Comparator.comparingLong(ServiceLinePayment::getCreatedAt))
                        .orElse(null);
                AtomicReference<Double> payment = new AtomicReference<>((double) 0);
                AtomicReference<Double> adjust = new AtomicReference<>((double) 0);
                groupingServiceLines.forEach(serviceLinePayment -> {
                    payment.set(payment.get() + serviceLinePayment.getPayment());
                    adjust.set(adjust.get() + serviceLinePayment.getAdjust());
                });
                ServiceLinePayment latestServiceLine = list.stream()
                        .filter(serviceLinePayment -> serviceLinePayment.equals(mostServiceLinePayment))
                                .findFirst().get();
                latestServiceLine.setPayment(payment.get());
                latestServiceLine.setAdjust(adjust.get());
                result.add(latestServiceLine);
            }else{
                result.addAll(groupingServiceLines);
            }
        }
        return result;
    }
}
