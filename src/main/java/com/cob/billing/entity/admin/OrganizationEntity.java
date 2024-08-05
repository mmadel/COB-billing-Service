package com.cob.billing.entity.admin;

import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.admin.OrganizationData;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "organization")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Getter
@Setter
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "npi")
    private String npi;
    @Column(name = "data", columnDefinition = "json")
    @Type(type = "jsonb")
    private OrganizationData organizationData;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrganizationType type;
}
