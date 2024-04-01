package com.cob.billing.usecases.bill.posting;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePayment;
import com.cob.billing.repositories.bill.posting.PatientSessionServiceLinePaymentRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindSessionPaymentUseCase {
    @Autowired
    private PatientSessionServiceLinePaymentRepository patientSessionServiceLinePaymentRepository;


    public List<ServiceLinePayment> findByServiceLines(List<Long> serviceLinesIds) {
        return patientSessionServiceLinePaymentRepository.findByServiceLines(serviceLinesIds).get();
    }
}
