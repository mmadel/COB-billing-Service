package com.cob.billing.usecases.bill.history.tmp;

import com.cob.billing.entity.bill.invoice.tmp.PatientInvoiceRecord;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.tmp.PatientInvoiceRecordRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindSessionHistoryUseCase {
    @Autowired
    PatientInvoiceRecordRepository patientInvoiceRecordRepository;
    @Autowired
    MapSessionHistoryUseCase mapSessionHistoryUseCase;

    public SessionHistoryResponse find(Pageable paging) {
        List<PatientInvoiceRecord> patientInvoiceRecords = patientInvoiceRecordRepository.findAllByOrderByCreatedAtDesc();
        List<SessionHistory> sessionHistories = mapSessionHistoryUseCase.map(patientInvoiceRecords);
        List<SessionHistory> paginate = PaginationUtil.paginate(sessionHistories, paging.getPageNumber() + 1, paging.getPageSize());
        return SessionHistoryResponse.builder()
                .number_of_records((int) sessionHistories.size())
                .number_of_matching_records((int) paginate.size())
                .records(paginate)
                .build();
    }

}
