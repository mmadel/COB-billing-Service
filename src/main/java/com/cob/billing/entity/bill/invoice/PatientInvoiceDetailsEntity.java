package com.cob.billing.entity.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.PatientSessionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientInvoiceDetailsEntity that = (PatientInvoiceDetailsEntity) o;
        return serviceLine.equals(that.serviceLine) && patientSession.equals(that.patientSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceLine, patientSession);
    }
}
