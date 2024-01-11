package com.cob.billing.entity.clinical.insurance.compnay;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_invoice_internal_company")
@Getter
@Setter
public class PatientInvoiceInternalCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "patient_Insurance_id", referencedColumnName = "id")
    PatientInvoiceEntity internalPatientInvoice;

    @OneToOne
    @JoinColumn(name = "internal_insurance_company_id", referencedColumnName = "id")
    InsuranceCompanyEntity internalInsuranceCompany;
}
