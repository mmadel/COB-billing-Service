package com.cob.billing.usecases.integration.claim.md;

import com.cob.billing.model.integration.claimmd.era.ERADetailsModel;
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
public class RetrieveERADetailsUseCase {
    @Value("${claim_md_api_key}")
    private String apiKey;
    @Value("${claim_md_bas_url}")
    private String claimMDcBaseURL;

    @Value("${claim_md_era_details}")
    private String claimMDERADetails;
    @Autowired
    private RestTemplate restTemplate;

    public ERADetailsModel get(Integer eraId){
        String url = this.claimMDcBaseURL + this.claimMDERADetails;
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("AccountKey", apiKey);
        builder.part("eraid", eraId);
        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> request = new HttpEntity<>(multipartRequest, headers);
        return  restTemplate.exchange(
                url, HttpMethod.POST, request, ERADetailsModel.class).getBody();
    }
}
