package com.cob.billing.entity.bill.era;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "claim_adjustment_reason_codes")
@Setter
@Getter
public class ClaimAdjustmentReasonCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;


    @Column(name = "description" , length = 1024)
    private String description;
}
