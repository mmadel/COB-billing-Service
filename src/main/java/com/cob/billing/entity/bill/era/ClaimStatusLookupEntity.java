package com.cob.billing.entity.bill.era;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "claim_status_lookup")
@Setter
@Getter
public class ClaimStatusLookupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status_id")
    private String statusId;

    @Column(name = "description")
    private String description;

}
