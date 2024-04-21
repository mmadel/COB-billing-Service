package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.bill.balance.PatientBalanceSettingsEntity;
import com.cob.billing.model.bill.posting.balance.PatientBalanceSettings;
import com.cob.billing.repositories.bill.balance.ClientBalanceSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RetrieveClientBalanceSettingsUseCase {
    @Autowired
    ClientBalanceSettingsRepository clientBalanceSettingsRepository;
    @Autowired
    ModelMapper mapper;

    @Cacheable("balance-statement-settings")
    public PatientBalanceSettings retrieve() {
        PatientBalanceSettingsEntity balanceSettings = clientBalanceSettingsRepository.findAllByIdIsNotNull().get();
        return mapper.map(balanceSettings, PatientBalanceSettings.class);
    }
}
