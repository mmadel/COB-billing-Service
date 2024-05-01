package com.cob.billing.usecases.bill;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.posting.paymnet.BatchSessionServiceLinePayment;
import com.cob.billing.model.response.SessionServiceLinePaymentResponse;
import com.cob.billing.usecases.bill.posting.ConstructBatchServiceLinesPaymentsUseCase;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BuildSessionServiceLinePayment {
    @Autowired
    ConstructBatchServiceLinesPaymentsUseCase constructBatchServiceLinesPaymentsUseCase;

    public SessionServiceLinePaymentResponse build(List<PatientSessionEntity> sessions, int offset, int limit) {
        List<BatchSessionServiceLinePayment> response = constructBatchServiceLinesPaymentsUseCase.construct(sessions);
        response.sort(Comparator.comparingLong(BatchSessionServiceLinePayment::getDos));
        List<BatchSessionServiceLinePayment> records = PaginationUtil.paginate(response, offset, limit);
        return SessionServiceLinePaymentResponse.builder()
                .number_of_records(response.size())
                .number_of_matching_records((int) records.size())
                .records(records)
                .build();

    }
}
