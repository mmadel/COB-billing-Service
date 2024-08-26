package com.cob.billing.entity.clinical.patient.claim;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.enums.SubmissionStatus;
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
@Table(name = "patient_claim")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Getter
@Setter
public class PatientClaimEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "claim_id")
    private String claimId;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status")
    private ClaimResponseStatus submissionStatus;

    @Column(name = "submission_messages", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    List<String> submissionMessages;

    @ManyToOne
    @JoinColumn(name = "patient_invoice_id")
    private PatientInvoiceEntity patientInvoice;
}
