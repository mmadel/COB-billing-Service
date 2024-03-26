package com.cob.billing.entity.bill.fee.schedule;

import com.cob.billing.enums.RateType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fee_schedule_line")
@Setter
@Getter
public class FeeScheduleLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cpt_code")
    private String cptCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "rate_type")
    private RateType rateType;
    @Column(name = "per_unit")
    private Integer perUnit;
    @Column(name = "charge_amount")
    private Float chargeAmount;
    @ManyToOne
    @JoinColumn(name="fee_schedule_id")
    private FeeScheduleEntity feeSchedule;

}
