package com.cob.billing.entity.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.bill.invoice.request.InvoiceInsuranceCompanyInformation;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patient_invoice")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class PatientInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patient;

    @OneToMany(mappedBy = "patientInvoice")
    Set<PatientInvoiceDetailsEntity> invoiceDetails;

    @OneToMany(mappedBy = "patientInvoice")
    Set<PatientClaimEntity> patientClaims;

    @Column(name = "is_one_date_service_per_claim")
    private Boolean isOneDateServicePerClaim;

    @Column(name = "delayed_reason")
    private String delayedReason;


    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "insurance_company", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private InvoiceInsuranceCompanyInformation insuranceCompany;

    @Column(name = "submission_id")
    private Long submissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_type")
    private SubmissionType submissionType;
    
    @Column(name = "document", length = 100000)
    private byte[] cmsDocument;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
