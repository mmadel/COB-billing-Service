package com.cob.billing.usecases.bill.history;

import com.cob.billing.model.bill.invoice.search.SessionHistoryCriteria;
import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchSessionHistoryUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    public void search(int offset, int limit, SessionHistoryCriteria sessionHistoryCriteria) {
        patientInvoiceRepository.search(sessionHistoryCriteria.getInsuranceCompany() != null ? sessionHistoryCriteria.getInsuranceCompany().toUpperCase().trim() : null
                , sessionHistoryCriteria.getClient() != null ? sessionHistoryCriteria.getClient().toUpperCase().trim() : null
                , sessionHistoryCriteria.getProvider() != null ? sessionHistoryCriteria.getProvider().toUpperCase().trim() : null
                , sessionHistoryCriteria.getDosStart() != null ? sessionHistoryCriteria.getDosStart() : null
                , sessionHistoryCriteria.getDosEnd() != null ? sessionHistoryCriteria.getDosEnd() : null
                , sessionHistoryCriteria.getSubmitStart() != null ? sessionHistoryCriteria.getSubmitStart() : null
                , sessionHistoryCriteria.getSubmitEnd() != null ? sessionHistoryCriteria.getSubmitEnd() : null);
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
