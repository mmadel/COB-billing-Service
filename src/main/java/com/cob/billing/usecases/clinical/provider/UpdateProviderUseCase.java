package com.cob.billing.usecases.clinical.provider;

import com.cob.billing.entity.clinical.provider.ProviderEntity;
import com.cob.billing.model.clinical.provider.Provider;
import com.cob.billing.repositories.clinical.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProviderUseCase {
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ModelMapper mapper;
    public Long update(Provider provider){
        ProviderEntity tooBeUpdate = mapper.map(provider , ProviderEntity.class);
        providerRepository.save(tooBeUpdate);
        return provider.getId();
    }
}
