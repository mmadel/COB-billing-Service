package com.cob.billing.entity.bill.payment;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.ServiceLinePaymentAction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_session_service_line_payment")
@Setter
@Getter
public class PatientSessionServiceLinePaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "payment")
    private double payment;

    @Column(name = "adjust")
    private double adjust;

    @Column(name = "created_at")
    private Long createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_action")
    private ServiceLinePaymentAction serviceLinePaymentAction;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_Line", referencedColumnName = "id")
    private PatientSessionServiceLineEntity serviceLine;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_Info_id")
    PatientSessionServiceLinePaymentInfoEntity patientSessionServiceLinePaymentInfoEntity;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
