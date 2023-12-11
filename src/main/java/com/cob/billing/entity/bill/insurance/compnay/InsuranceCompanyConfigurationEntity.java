package com.cob.billing.entity.bill.insurance.compnay;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "insurance_company_configuration")
@Getter
@Setter
public class InsuranceCompanyConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "box_32")
    private Boolean box32;
    @Column(name = "box_26")
    private String box26;
    @Column(name = "box_33")
    private Long box33;
    @Column(name = "insurance_company_identifier")
    private Long insuranceCompanyIdentifier;
    @Column(name = "is_assigned_to_payer")
    private Boolean isAssignedToPayer;
}
