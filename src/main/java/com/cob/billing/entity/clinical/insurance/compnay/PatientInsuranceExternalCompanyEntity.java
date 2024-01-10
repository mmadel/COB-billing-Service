package com.cob.billing.entity.clinical.insurance.compnay;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_insurance_external_company")
@Getter
@Setter
public class PatientInsuranceExternalCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "patient_Insurance_id", referencedColumnName = "id")
    PatientInsuranceEntity externalPatientInsurance;

    @OneToOne
    @JoinColumn(name = "external_insurance_company_id", referencedColumnName = "id")
    InsuranceCompanyExternalEntity insuranceCompany;
}
