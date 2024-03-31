package com.cob.billing.entity.bill.payment;

import com.cob.billing.enums.ServiceLinePaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_session_service_line_payment_detail")
@Setter
@Getter
public class PatientSessionServiceLinePaymentDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private ServiceLinePaymentType serviceLinePaymentType;

    @Column(name = "total_amount")
    private Long totalAmount;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "received_date")
    private Long receivedDate;
    @Column(name = "check_date")
    private Long checkDate;
    @Column(name = "check_number")
    private Long checkNumber;
    @Column(name = "deposit_date")
    private Long depositDate;
    @Column(name = "insurance_company")
    private String insuranceCompany;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private PatientSessionServiceLinePaymentEntity patientSessionServiceLinePayment;
}
