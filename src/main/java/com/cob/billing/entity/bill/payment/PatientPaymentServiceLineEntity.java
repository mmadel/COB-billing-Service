package com.cob.billing.entity.bill.payment;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.enums.SessionAction;
import com.cob.billing.model.bill.posting.PaymentBatch;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "patient_payment_service_line")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class PatientPaymentServiceLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "session_id")
    private Long sessionId;
    @Column(name = "service_code_id")
    private Long ServiceCodeId;
    @Column(name = "date_of_service")
    private Long dateOfService;
    @Column(name = "cpt")
    private String cpt;
    @Column(name = "provider")
    private String provider;
    @Column(name = "billed_value")
    private double billedValue;
    @Column(name = "previous_payments")
    private double previousPayments;
    @Column(name = "payment")
    private double payment;
    @Column(name = "adjust")
    private double adjust;
    @Column(name = "balance")
    private double balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "session_action")
    private SessionAction sessionAction;
    @Column(name = "payment_batch", columnDefinition = "json")
    @Type(type = "json")
    private PaymentBatch paymentBatch;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
}
