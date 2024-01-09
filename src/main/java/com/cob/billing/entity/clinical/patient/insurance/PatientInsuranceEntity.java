package com.cob.billing.entity.clinical.patient.insurance;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.clinical.patient.insurance.PatientInsuranceAdvanced;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurancePolicy;
import com.cob.billing.model.clinical.patient.insurance.PatientRelation;
import com.cob.billing.model.common.Address;
import com.cob.billing.model.common.BasicAddress;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "patient_insurance")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class PatientInsuranceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String relation;

    @Column(name = "Patient_relation", columnDefinition = "json")
    @Type(type = "json")
    private PatientRelation patientRelation;

    @Column(name = "patient_insurance_policy", columnDefinition = "json")
    @Type(type = "json")
    private PatientInsurancePolicy patientInsurancePolicy;

    @Column(name = "patient_insurance_advanced", columnDefinition = "json")
    @Type(type = "json")
    private PatientInsuranceAdvanced patientInsuranceAdvanced;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientEntity patient;

    @OneToOne
    @JoinColumn(name = "insurance_company_id")
    private InsuranceCompanyEntity insuranceCompany;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @Column(name = "payer", columnDefinition = "json")
    @Type(type = "json")
    private Payer payer;

}
