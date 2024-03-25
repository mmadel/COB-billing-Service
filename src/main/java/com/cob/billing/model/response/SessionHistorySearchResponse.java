package com.cob.billing.model.response;

import com.cob.billing.model.history.SessionHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SessionHistorySearchResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<SessionHistory> records;
}
