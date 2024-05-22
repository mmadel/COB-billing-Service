package com.cob.billing.usecases.clinical.provider;

import com.cob.billing.repositories.clinical.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteProviderUseCase {
    @Autowired
    ProviderRepository providerRepository;
    public Long delete(Long id){
        providerRepository.deleteById(id);
        return id;
    }
}
