package com.cob.billing.entity.clinical.provider;

import com.cob.billing.model.clinical.provider.LegacyID;
import com.cob.billing.model.clinical.provider.ProviderInfo;
import com.cob.billing.model.common.Address;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "provider")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class ProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "npi")
    private String npi;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address", columnDefinition = "json")
    @Type(type = "json")
    private Address address;
    @Column(name = "provider_info", columnDefinition = "json")
    @Type(type = "json")
    private ProviderInfo ProviderInfo;

    @Column(name = "legacy_id", columnDefinition = "json")
    @Type(type = "json")
    private LegacyID legacyID;
}
