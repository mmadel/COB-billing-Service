package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.model.integration.claimmd.ClaimUploadRequest;
import com.cob.billing.usecases.bill.invoice.MultipleItemsChecker;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.usecases.bill.invoice.electronic.creator.multiple.CreateElectronicMultipleClaimUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.single.CreateElectronicSingleClaimUseCase;
import com.cob.billing.util.BeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Qualifier("ElectronicBillingClaim")
public class ElectronicBillingClaim extends BillingClaim {
    @Autowired
    private RestTemplate restTemplate;
    ElectronicClaimCreator electronicClaimCreator;
    List<Claim> claims;
    @Value("${claim_md_api_key}")
    private String apiKey;
    @Value("${claim_md_submit_url}")
    private String submitClaimURL;

    @Override
    public void pickClaimProvider() {
        Boolean hasMultipleItems = MultipleItemsChecker.check(invoiceRequest);
        flags = MultipleItemsChecker.getMultipleFlags();
        if (hasMultipleItems)
            electronicClaimCreator = BeanFactory.getBean(CreateElectronicMultipleClaimUseCase.class);
        else
            electronicClaimCreator = BeanFactory.getBean(CreateElectronicSingleClaimUseCase.class);
    }

    @Override
    public void createClaim() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        claims = electronicClaimCreator.create(invoiceRequest, flags);

    }

    @Override
    public void submitClaim() throws IOException {
        //submit to Clearing House
        ClaimUploadRequest claimUploadRequest = constructClaimRequest();
        FileSystemResource claimResource = createClaimFile(claimUploadRequest);
        //send(claimResource);
        //claimResource.getFile().delete();

    }

    private ClaimUploadRequest constructClaimRequest() {
        Random random = new Random();
        Integer fileId = random.nextInt(900) + 100;
        return ClaimUploadRequest.builder()
                .claim(claims)
                .fileid(fileId.toString())
                .build();
    }

    private FileSystemResource createClaimFile(ClaimUploadRequest claimUploadRequest) throws IOException {
        String fileName = "claim_" + new Date().getTime() + ".json";
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, claimUploadRequest);
        return new FileSystemResource(file);
    }

    private void send(FileSystemResource fileResource) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("File", fileResource);
        builder.part("AccountKey", apiKey);
        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> request = new HttpEntity<>(multipartRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                submitClaimURL, HttpMethod.POST, request, String.class);
    }
}
