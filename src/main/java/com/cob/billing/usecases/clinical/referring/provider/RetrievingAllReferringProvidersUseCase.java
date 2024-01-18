package com.cob.billing.usecases.clinical.referring.provider;

import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.model.response.ReferringProviderResponse;
import com.cob.billing.repositories.clinical.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetrievingAllReferringProvidersUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    ModelMapper mapper;

    public ReferringProviderResponse findAll(Pageable paging) {
        Page<ReferringProviderEntity> pages = referringProviderRepository.findAll(paging);
        long total = (pages).getTotalElements();
        List<ReferringProvider> records = pages.stream().map(referringProvider -> mapper.map(referringProvider, ReferringProvider.class))
                .collect(Collectors.toList());
        return ReferringProviderResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    public List<ReferringProvider> findAll(){
        List<ReferringProvider> referringProviders = new ArrayList<>();
        for (ReferringProviderEntity referringProvider : referringProviderRepository.findAll()) {
            referringProviders.add(mapper.map(referringProvider, ReferringProvider.class));
        }
        return referringProviders;
    }
}
