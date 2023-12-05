package com.cob.billing.usecases.clinical.provider;

import com.cob.billing.entity.clinical.provider.ProviderEntity;
import com.cob.billing.model.clinical.provider.Provider;
import com.cob.billing.model.response.ProviderResponse;
import com.cob.billing.repositories.clinical.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindProvidersUseCase {

    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ModelMapper mapper;

    public ProviderResponse findAll(Pageable paging) {
        Page<ProviderEntity> pages = providerRepository.findAll(paging);
        long total = (pages).getTotalElements();
        List<Provider> records = pages.stream().map(provider -> mapper.map(provider, Provider.class))
                .collect(Collectors.toList());
        return ProviderResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
    public List<Provider> findAll(){
        List<Provider> providers = new ArrayList<>();
        for (ProviderEntity provider : providerRepository.findAll()) {
            providers.add(mapper.map(provider, Provider.class));
        }
        return providers;
    }
}
