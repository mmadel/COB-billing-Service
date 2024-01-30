package com.cob.billing.usecases.integration.nppes;

import com.cob.billing.model.clinical.provider.Provider;
import com.cob.billing.model.clinical.provider.ProviderInfo;
import com.cob.billing.model.common.Address;
import com.cob.billing.model.integration.nppes.NPPESResponse;
import com.cob.billing.model.integration.nppes.Result;
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

    public Provider find(Long npi) {
        String url = nppesBaseUrl + "?version=2.1&number=" + npi;
        NPPESResponse result = restTemplate.getForObject(url, NPPESResponse.class);
        return map(result);
    }

    private Provider map(NPPESResponse nppesResponse) {
        Provider provider = new Provider();
        if (nppesResponse.result_count != 0) {
            Result result = nppesResponse.getResults().get(0);
            provider.setFirstName(result.getBasic().getFirst_name());
            provider.setLastName(result.getBasic().last_name);
            provider.setPhone(result.getAddresses().get(0).getTelephone_number());
            provider.setNpi(result.number);
            Address address = new Address();
            address.setFirst(result.getAddresses().get(0).address_1);
            address.setSecond(result.getAddresses().get(0).address_2);
            address.setCountry(result.getAddresses().get(0).country_name);
            address.setCity(result.getAddresses().get(0).city);
            address.setState(result.getAddresses().get(0).state);
            address.setZipCode(result.getAddresses().get(0).postal_code);
            provider.setAddress(address);
            ProviderInfo providerInfo = new ProviderInfo();
            providerInfo.setTaxonomyCode(result.getTaxonomies().get(0).code);
            providerInfo.setLicense(result.getTaxonomies().get(0).license);
            provider.setProviderInfo(providerInfo);
        }
        return provider;
    }
}
