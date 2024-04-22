package com.cob.billing.model.bill.fee.schedule;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.provider.SimpleProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeeScheduleMetaData {
    List<SimpleProvider> providers;
    List<InsuranceCompanyHolder> insurances;
}
