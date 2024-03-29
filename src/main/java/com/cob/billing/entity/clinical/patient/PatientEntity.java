package com.cob.billing.entity.clinical.patient;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.enums.Gender;
import com.cob.billing.enums.MaritalStatus;
import com.cob.billing.model.clinical.patient.advanced.PatientAdvancedInformation;
import com.cob.billing.model.common.Address;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private Long birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "phone_type")
    private String phoneType;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "copay")
    private String copay;
    @Column(name = "patient_addresses", columnDefinition = "json")
    @Type(type = "json")
    private Address address;

    @Column(name = "patient_advanced_information", columnDefinition = "json")
    @Type(type = "json")
    private PatientAdvancedInformation patientAdvancedInformation;
    @OneToMany(mappedBy = "patient")
    private List<PatientCaseEntity> cases;

    @ManyToOne
    @JoinColumn(name = "referring_provider_id")
    private ReferringProviderEntity referringProvider;

    @OneToMany( mappedBy = "patient")
    private List<PatientInsuranceEntity> insurances;

    @OneToMany( mappedBy = "patient")
    private List<PatientSessionEntity> sessions;

    @Column(name = "ssn")
    private String ssn;
    @Column(name = "external_id")
    private String externalId;


}
