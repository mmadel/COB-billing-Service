package com.cob.billing.entity.clinical.patient.insurance;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.insurance.PatientInsuranceAdvanced;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurancePolicy;
import com.cob.billing.model.clinical.patient.insurance.PatientRelation;
import com.cob.billing.model.common.Address;
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

    @Column(name = "payer_address", columnDefinition = "json")
    @Type(type = "json")
    private Address payerAddress;
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
    @Column(name = "is_archived")
    private Boolean isArchived;
    @Column(name="insurance_company")
    private Long insuranceCompany;

}
