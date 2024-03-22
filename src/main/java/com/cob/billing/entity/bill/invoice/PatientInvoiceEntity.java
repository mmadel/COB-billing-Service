package com.cob.billing.entity.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_invoice")
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

    @Column(name = "is_one_date_service_per_claim")
    private Boolean isOneDateServicePerClaim;

    @Column(name = "delayed_reason")
    private String delayedReason;

    @OneToOne
    @JoinColumn(name = "service_line_id")
    private PatientSessionServiceLineEntity serviceLine;

    @ManyToOne
    @JoinColumn(name = "patient_session_id")
    private PatientSessionEntity patientSession;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "insurance_company_id")
    private Long insuranceCompanyId;

    @Column(name = "submission_id")
    private Long submissionId;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
