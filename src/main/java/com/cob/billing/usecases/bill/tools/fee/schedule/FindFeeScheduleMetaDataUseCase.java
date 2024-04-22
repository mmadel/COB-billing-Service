package com.cob.billing.usecases.bill.tools.fee.schedule;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleMetaData;
import com.cob.billing.model.clinical.provider.SimpleProvider;
import com.cob.billing.usecases.clinical.patient.insurance.company.FindInsuranceCompaniesUseCase;
import com.cob.billing.usecases.clinical.provider.FindProvidersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FindFeeScheduleMetaDataUseCase {
    @Autowired
    FindProvidersUseCase findProvidersUseCase;

    @Autowired
    FindInsuranceCompaniesUseCase findInsuranceCompaniesUseCase;

    public FeeScheduleMetaData find() {
        FeeScheduleMetaData data = new FeeScheduleMetaData();
        data.setProviders(findProvidersUseCase.findAll().stream()
                .map(provider -> {
                    SimpleProvider simpleProvider = new SimpleProvider();
                    simpleProvider.setId(provider.getId());
                    simpleProvider.setFirstName(provider.getFirstName());
                    simpleProvider.setLastName(provider.getLastName());
                    simpleProvider.setNpi(provider.getNpi());
                    return simpleProvider;
                })
                .collect(Collectors.toList()));
        data.setInsurances(findInsuranceCompaniesUseCase.find());
        return data;
    }
}
