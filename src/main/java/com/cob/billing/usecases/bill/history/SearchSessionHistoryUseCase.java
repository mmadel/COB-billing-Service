package com.cob.billing.usecases.bill.history;

import com.cob.billing.model.bill.invoice.search.SessionHistoryCriteria;
import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.util.PaginationUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchSessionHistoryUseCase {
    public void search(SessionHistoryCriteria sessionHistoryCriteria) {

    }

    private SessionHistoryResponse constructSessionHistoryResponse(int offset, int limit) {
        List<PatientSessionServiceLine> records = PaginationUtil.paginate(null, offset, limit);
        return SessionHistoryResponse.builder()
                .number_of_records(null) //patientSessionServiceLines.size()
                .number_of_matching_records(records.size())
                .records(null) //records
                .build();
    }
}
