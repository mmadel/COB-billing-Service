package com.cob.billing.entity.clinical.referring.provider;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "referring_provider")
@Getter
@Setter
public class ReferringProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "npi")
    private String npi;
    @Column(name = "referring_provider_id")
    private String referringProviderId;
    @Column(name = "referring_provider_qualifier")
    private String referringProviderIdQualifier;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "referringProvider")
    List<PatientEntity> patients = new ArrayList<>();
}
