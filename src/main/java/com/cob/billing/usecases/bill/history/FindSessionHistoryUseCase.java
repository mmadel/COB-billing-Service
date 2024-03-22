package com.cob.billing.usecases.bill.history;

import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.util.PaginationUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindSessionHistoryUseCase {
    public SessionHistoryResponse find(int offset, int limit) {
        List<SessionHistory> result = new ArrayList<>();
        result.add(
                SessionHistory.builder()
                        .submissionId(30303L)
                        .provider("ahmed mohamed")
                        .client("wael")
                        .insuranceCompany("Axa")
                        .dateOfService(1713045600000L)
                        .submitDate(1717189200000L)
                        .numberOfServiceLines(3)
                        .submissionStatus(SubmissionStatus.Submit)
                        .submissionType(SubmissionType.Print)
                        .build());
        result.add(
                SessionHistory.builder()
                        .submissionId(3839L)
                        .provider("Samy")
                        .client("hany")
                        .dateOfService(1713045600000L)
                        .submitDate(1717189200000L)
                        .numberOfServiceLines(5)
                        .submissionType(SubmissionType.Print)
                        .submissionStatus(SubmissionStatus.Submit)
                        .insuranceCompany("MetLife").build());
        result.add(
                SessionHistory.builder()
                        .submissionId(3839L)
                        .provider("Samy")
                        .client("hany")
                        .dateOfService(1707861600000L)
                        .submitDate(1719781200000L)
                        .numberOfServiceLines(2)
                        .submissionType(SubmissionType.Print)
                        .submissionStatus(SubmissionStatus.error)
                        .insuranceCompany("MetLife").build());
        result.add(
                SessionHistory.builder()
                        .submissionId(3839L)
                        .provider("Samy")
                        .client("hany")
                        .dateOfService(1707861600000L)
                        .submitDate(1719781200000L)
                        .numberOfServiceLines(2)
                        .submissionType(SubmissionType.Electronic)
                        .submissionStatus(SubmissionStatus.acknowledge)
                        .insuranceCompany("MetLife").build());
        List<SessionHistory> records = PaginationUtil.paginate(result, offset, limit);
        return SessionHistoryResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) result.size())
                .records(records)
                .build();
    }
}
