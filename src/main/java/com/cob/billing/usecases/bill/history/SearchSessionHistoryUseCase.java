package com.cob.billing.usecases.bill.history;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.model.bill.invoice.search.SessionHistoryCriteria;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.PatientClaimRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchSessionHistoryUseCase {
    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;

    @Autowired
    private MapSessionHistoryUseCase mapSessionHistoryUseCase;

    @Autowired
    private PatientClaimRepository patientClaimRepository;

    public SessionHistoryResponse search(int offset, int limit, SessionHistoryCriteria sessionHistoryCriteria) {
        List<PatientInvoiceEntity> invoiceEntities = patientInvoiceRepository.search(sessionHistoryCriteria.getInsuranceCompany() != null ? sessionHistoryCriteria.getInsuranceCompany().toUpperCase().trim() : null
                , sessionHistoryCriteria.getClient() != null ? sessionHistoryCriteria.getClient().toUpperCase().trim() : null
                , sessionHistoryCriteria.getProvider() != null ? sessionHistoryCriteria.getProvider().toUpperCase().trim() : null
                , sessionHistoryCriteria.getDosStart() != null ? sessionHistoryCriteria.getDosStart() : null
                , sessionHistoryCriteria.getDosEnd() != null ? sessionHistoryCriteria.getDosEnd() : null
                , sessionHistoryCriteria.getSubmitStart() != null ? sessionHistoryCriteria.getSubmitStart() : null
                , sessionHistoryCriteria.getSubmitEnd() != null ? sessionHistoryCriteria.getSubmitEnd() : null
                , sessionHistoryCriteria.getSelectedStatus() != null ? getListClaimResponseStatus(sessionHistoryCriteria.getSelectedStatus()) : null);
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

    private List<ClaimResponseStatus> getListClaimResponseStatus(List<SubmissionStatus> selectedStatus) {
        Map<SubmissionStatus, ClaimResponseStatus> statusMapping =
                Map.of(
                        SubmissionStatus.Success, ClaimResponseStatus.Claim_Success,
                        SubmissionStatus.Pending, ClaimResponseStatus.Claim_Acknowledgment,
                        SubmissionStatus.acknowledge, ClaimResponseStatus.Claim_Acknowledgment,
                        SubmissionStatus.error, ClaimResponseStatus.Claim_Rejection
                );
        return selectedStatus.stream()
                .map(statusMapping::get)
                .collect(Collectors.toList());

    }
}
