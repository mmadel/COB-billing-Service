package com.cob.billing.entity.clinical.insurance.compnay;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_invoice_external_company")
@Getter
@Setter
public class PatientInvoiceExternalCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_Insurance_id", referencedColumnName = "id")
    PatientInvoiceEntity externalPatientInvoice;

    @OneToOne
    @JoinColumn(name = "external_insurance_company_id", referencedColumnName = "id")
    InsuranceCompanyExternalEntity externalInsuranceCompany;
}
