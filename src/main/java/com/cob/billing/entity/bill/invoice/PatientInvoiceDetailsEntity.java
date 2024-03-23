package com.cob.billing.entity.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_invoice_details")
@Setter
@Getter
public class PatientInvoiceDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "service_line_id")
    private PatientSessionServiceLineEntity serviceLine;

    @ManyToOne
    @JoinColumn(name = "patient_session_id")
    private PatientSessionEntity patientSession;

    @ManyToOne
    @JoinColumn(name = "patient_invoice_id")
    private PatientInvoiceEntity patientInvoice;
}
