package com.cob.billing.usecases.clinical.patient.session.update;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class SessionEvent extends ApplicationEvent {
    private PatientSession session;
    private List<PatientSubmittedClaim> submittedSessions;
    public SessionEvent(Object source, PatientSession session,List<PatientSubmittedClaim> submittedSessions) {
        super(source);
        this.session = session;
        this.submittedSessions = submittedSessions;
    }

    public PatientSession getSession(){
        return session;
    }
    public List<PatientSubmittedClaim> getSubmittedSessions(){
        return submittedSessions;
    }
}
