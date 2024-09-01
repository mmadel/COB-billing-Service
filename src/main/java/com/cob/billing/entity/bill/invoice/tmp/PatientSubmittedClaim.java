package com.cob.billing.entity.bill.invoice.tmp;

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

    @Column(name = "provider_name")
    private String providerName;

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
}
