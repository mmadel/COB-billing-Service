package com.cob.billing.entity.bill.invoice.tmp;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient_submitted_claim")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class PatientSubmittedClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider_first_name")
    private String providerFirstName;

    @Column(name = "provider_last_name")
    private String providerLastName;

    @Column(name = "provider_npi")
    private String provider_npi;

    @Column(name = "service_lines", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<ServiceLine> serviceLine;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status")
    private SubmissionStatus submissionStatus;

    @Column(name = "local_claim_id")
    private Long localClaimId;

    @Column(name = "remote_claim_id")
    private Long remoteClaimId;

    @Column(name = "submitted_claim_message", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<String> messages;

    @Column(name = "dos")
    private Long dateOfService;

    @ManyToOne
    @JoinColumn(name = "patient_invoice_record_id")
    private PatientInvoiceRecord patientInvoiceRecord;

    @OneToOne
    @JoinColumn(name = "patient_session_id")
    private PatientSessionEntity patientSession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientSubmittedClaim claim = (PatientSubmittedClaim) o;
        return patientSession.getId().equals(claim.patientSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientSession.getId());
    }
}
