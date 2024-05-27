package com.cob.billing.usecases.clinical.referring.provider;

import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.repositories.clinical.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateReferringProviderUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    ModelMapper mapper;
    public Long update(ReferringProvider referringProvider){
        ReferringProviderEntity toBeCreated = mapper.map(referringProvider, ReferringProviderEntity.class);
        referringProviderRepository.save(toBeCreated);
        return referringProvider.getId();
    }
}
