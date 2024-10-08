package com.cob.billing.model.history;

import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class SessionHistoryCount {
    private Long sessionId;
    private Long serviceLines;
    private List<ServiceLine> serviceLine;
    private Long dateOfService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionHistoryCount that = (SessionHistoryCount) o;
        return sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
