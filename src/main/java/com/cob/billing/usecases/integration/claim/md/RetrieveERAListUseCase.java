package com.cob.billing.usecases.integration.claim.md;

import com.cob.billing.model.integration.claimmd.era.ERAListResponse;
import com.cob.billing.model.integration.claimmd.status.updates.StatusUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RetrieveERAListUseCase {
    @Value("${claim_md_api_key}")
    private String apiKey;
    @Value("${claim_md_bas_url}")
    private String claimMDcBaseURL;

    @Value("${claim_md_era_list}")
    private String claimMDERAList;

    @Autowired
    private RestTemplate restTemplate;

    public ERAListResponse getList(Long eraId) {
        String url = this.claimMDcBaseURL + this.claimMDERAList;
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("AccountKey", apiKey);
        builder.part("ERAID", eraId);
        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> request = new HttpEntity<>(multipartRequest, headers);
        return  restTemplate.exchange(
                url, HttpMethod.POST, request, ERAListResponse.class).getBody();
    }
}
