package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.bill.balance.PatientBalanceSettingsEntity;
import com.cob.billing.model.bill.posting.balance.PatientBalanceSettings;
import com.cob.billing.repositories.bill.balance.ClientBalanceSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CreateOrUpdateClientBalanceSettingsUseCase {
    @Autowired
    ClientBalanceSettingsRepository clientBalanceSettingsRepository;
    @Autowired
    ModelMapper mapper;

    @CacheEvict(value="balance-statement-settings", allEntries=true)
    public Long create(PatientBalanceSettings model) {
        PatientBalanceSettingsEntity toBeCreated = mapper.map(model, PatientBalanceSettingsEntity.class);
        return clientBalanceSettingsRepository.save(toBeCreated).getId();
    }
}
