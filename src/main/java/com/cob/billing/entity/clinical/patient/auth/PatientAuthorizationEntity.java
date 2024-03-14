package com.cob.billing.entity.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_authorization")
@Getter
@Setter
public class PatientAuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "auth_number")
    private String authNumber;
    @Column(name = "ins_company_id")
    private Long patientInsuranceCompany;
    @Column(name = "ins_company_name")
    private String patientInsuranceCompanyName;
    @Column(name = "service_code")
    private String serviceCode;
    @Column(name = "start_date")
    private Long startDateNumber;
    @Column(name = "expire_date")
    private Long expireDateNumber;
    @Column(name = "visit")
    private Integer visit;
    @Column(name = "remaining")
    private Integer remaining;
    @OneToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

}
