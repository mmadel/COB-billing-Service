package com.cob.billing.usecases.integration.nppes;

import com.cob.billing.model.integration.nppes.NPPESResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FindProviderByNPIUseCase {

    @Autowired
    RestTemplate restTemplate;

    @Value("${nppes.base.url}")
    private String nppesBaseUrl;

    public NPPESResponse find(Long npi) {
        String url = nppesBaseUrl + "?version=2.1&number=" + npi;
        NPPESResponse result = restTemplate.getForObject(url, NPPESResponse.class);
        return result;
    }
}
