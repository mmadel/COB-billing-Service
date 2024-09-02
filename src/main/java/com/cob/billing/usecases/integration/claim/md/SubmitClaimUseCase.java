package com.cob.billing.usecases.integration.claim.md;

import com.cob.billing.model.integration.claimmd.submit.SubmitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SubmitClaimUseCase {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${claim_md_bas_url}")
    private String claimMDcBaseURL;

    @Value("${claim_md_submit}")
    private String claimMDSubmit;

    @Value("${claim_md_api_key}")
    private String apiKey;

    public SubmitResponse submit(FileSystemResource fileResource) {
        String url = this.claimMDcBaseURL + this.claimMDSubmit;
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("File", fileResource);
        builder.part("AccountKey", apiKey);
        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> request = new HttpEntity<>(multipartRequest, headers);
        return restTemplate.exchange(
                url, HttpMethod.POST, request, SubmitResponse.class).getBody();
    }
}
