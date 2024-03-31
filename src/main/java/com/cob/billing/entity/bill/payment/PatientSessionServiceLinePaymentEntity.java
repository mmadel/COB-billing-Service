package com.cob.billing.entity.bill.payment;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import com.cob.billing.model.bill.posting.PaymentBatch;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_action")
    private ServiceLinePaymentAction serviceLinePaymentAction;

    @Column(name = "payment_batch", columnDefinition = "json")
    @Type(type = "json")
    private PaymentBatch paymentBatch;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private ServiceLinePaymentType serviceLinePaymentType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_Line", referencedColumnName = "id")
    private PatientSessionServiceLineEntity serviceLine;
}
