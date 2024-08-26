package com.cob.billing.usecases.bill.history;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.search.SessionHistoryCriteria;
import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchSessionHistoryUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    @Autowired
    MapSessionHistoryUseCase mapSessionHistoryUseCase;

    public SessionHistoryResponse search(int offset, int limit, SessionHistoryCriteria sessionHistoryCriteria) {
        List<PatientInvoiceEntity> invoiceEntities = patientInvoiceRepository.search(sessionHistoryCriteria.getInsuranceCompany() != null ? sessionHistoryCriteria.getInsuranceCompany().toUpperCase().trim() : null
                , sessionHistoryCriteria.getClient() != null ? sessionHistoryCriteria.getClient().toUpperCase().trim() : null
                , sessionHistoryCriteria.getProvider() != null ? sessionHistoryCriteria.getProvider().toUpperCase().trim() : null
                , sessionHistoryCriteria.getDosStart() != null ? sessionHistoryCriteria.getDosStart() : null
                , sessionHistoryCriteria.getDosEnd() != null ? sessionHistoryCriteria.getDosEnd() : null
                , sessionHistoryCriteria.getSubmitStart() != null ? sessionHistoryCriteria.getSubmitStart() : null
                , sessionHistoryCriteria.getSubmitEnd() != null ? sessionHistoryCriteria.getSubmitEnd() : null);
        return constructSessionHistoryResponse(offset, limit, invoiceEntities);
    }

    private SessionHistoryResponse constructSessionHistoryResponse(int offset, int limit, List<PatientInvoiceEntity> invoiceEntities) {
        List<SessionHistory> result = mapSessionHistoryUseCase.map(invoiceEntities);
        List<SessionHistory> records = PaginationUtil.paginate(result, offset, limit);
        return SessionHistoryResponse.builder()
                .number_of_records(result.size())
                .number_of_matching_records(records.size())
                .records(records) //records
                .build();
    }
}
