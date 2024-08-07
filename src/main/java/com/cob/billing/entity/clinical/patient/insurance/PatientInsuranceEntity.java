package com.cob.billing.entity.clinical.patient.insurance;

import com.cob.billing.entity.clinical.insurance.compnay.PatientInsuranceExternalCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInsuranceInternalCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.insurance.PatientInsuranceAdvanced;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurancePolicy;
import com.cob.billing.model.clinical.patient.insurance.PatientRelation;
import com.cob.billing.model.common.BasicAddress;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
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
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Getter
@Setter
public class PatientInsuranceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "relation")
    private String relation;

    @Column(name = "Patient_relation", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private PatientRelation patientRelation;

    @Column(name = "patient_insurance_policy", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private PatientInsurancePolicy patientInsurancePolicy;

    @Column(name = "patient_insurance_advanced", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private PatientInsuranceAdvanced patientInsuranceAdvanced;

    @Column(name = "insurance_company_address", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private BasicAddress insuranceCompanyAddress;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientInsurance")
    private PatientInsuranceInternalCompanyEntity patientInsuranceInternalCompany;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "externalPatientInsurance")
    private PatientInsuranceExternalCompanyEntity patientInsuranceExternalCompany;

    @Column(name = "createdAt")
    private Long createdAt;
}
