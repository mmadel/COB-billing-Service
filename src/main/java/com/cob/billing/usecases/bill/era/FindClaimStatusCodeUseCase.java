package com.cob.billing.usecases.bill.era;

import com.cob.billing.repositories.bill.era.ClaimStatusLookupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class FindClaimStatusCodeUseCase {
    @Autowired
    ClaimStatusLookupRepository claimStatusLookupRepository;
    @Cacheable("claim-status")
    public String find(String statusCode){
        return claimStatusLookupRepository.findByStatusId(statusCode).get().getDescription();
    }
}
