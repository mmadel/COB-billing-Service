package com.cob.billing.usecases.bill.posting.batching;

import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateBatchClientPaymentUseCase {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;

    @Transactional
    public void create(ServiceLinePaymentRequest serviceLinePaymentRequest) {
        //createSessionServiceLinePaymentUseCase.create(serviceLinePaymentRequest);

        Map<Long, List<SessionServiceLinePayment>> paymentsGroupedBySession = serviceLinePaymentRequest.getServiceLinePayments()
                .stream()
                .collect(Collectors.groupingBy(SessionServiceLinePayment::getSessionId));
    }
}
