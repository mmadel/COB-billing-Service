package com.cob.billing.entity.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
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

    @Column(name="insurance_company")
    private Long insuranceCompany;

    @Column(name = "is_one_date_service_per_claim")
    private Boolean isOneDateServicePerClaim;

    @Column(name = "delayed_reason")
    private String delayedReason;
    @Column(name = "service_code_id")
    private Long serviceCodeId;

    @Column(name="created_at")
    private Long createdAt;

    @PrePersist
    private void setCreatedDate(){
        createdAt = new Date().getTime();
    }



}
