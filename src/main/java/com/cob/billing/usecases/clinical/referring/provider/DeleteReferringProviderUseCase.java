package com.cob.billing.usecases.clinical.referring.provider;

import com.cob.billing.repositories.clinical.ReferringProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteReferringProviderUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;

    public Long delete(Long id) {
        referringProviderRepository.deleteById(id);
        return id;
    }
}
