package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.paymnet.ServiceLinePayment;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UpdateServiceLinesStatusUseCase {
    @Autowired
    ServiceLineRepository serviceLineRepository;

    public void update(List<ServiceLinePayment> serviceLinePayments) {
        List<Long> resubmittedServiceLines = new ArrayList<>();
        List<Long> closedServiceLines = new ArrayList<>();
        serviceLinePayments.forEach(paymentServiceLine -> {
            switch (paymentServiceLine.getServiceLinePaymentAction()) {
                case Resubmit:
                    resubmittedServiceLines.add(paymentServiceLine.getServiceLineId());
                    break;
                case Close:
                    closedServiceLines.add(paymentServiceLine.getServiceLineId());
                    break;
            }
            if (!resubmittedServiceLines.isEmpty())
                updateServiceLineStatus(resubmittedServiceLines, "Initial");
            if (!closedServiceLines.isEmpty())
                updateServiceLineStatus(closedServiceLines, "Close");
        });
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
