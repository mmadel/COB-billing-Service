package com.cob.billing.model.bill.fee.schedule;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.provider.SimpleProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeeScheduleModel {
    private SimpleProvider provider;
    private InsuranceCompanyHolder insurance;
    private String name;
    private Long id;
    private Boolean defaultFee;
    private Boolean active;
    private List<FeeScheduleLineModel> feeLines;
}
