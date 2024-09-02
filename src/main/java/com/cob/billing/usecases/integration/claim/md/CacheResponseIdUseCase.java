package com.cob.billing.usecases.integration.claim.md;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheResponseIdUseCase {

    private static final String CLAIM_MD = "CLAIM_MD";
    private static final String RESPONSE_ID = "response_id";
    @Autowired
    private CacheManager cacheManager;

    public Long getCachedNumber() {
        Cache cache = cacheManager.getCache(CLAIM_MD);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(RESPONSE_ID);
            if (valueWrapper != null) {
                return (Long) valueWrapper.get();
            }
        }
        return 0L;
    }

    public void updateCachedNumber(Long number) {
        Cache cache = cacheManager.getCache(CLAIM_MD);
        if (cache != null) {
            cache.put(RESPONSE_ID, number);
        }
    }

    public void clearCache() {
        Cache cache = cacheManager.getCache(CLAIM_MD);
        if (cache != null) {
            cache.evict(RESPONSE_ID);
        }
    }
}
