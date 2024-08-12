package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.model.integration.claimmd.ClaimUploadRequest;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.usecases.bill.invoice.electronic.creator.multiple.CreateElectronicMultipleClaimUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.single.CreateElectronicSingleClaimUseCase;
import com.cob.billing.usecases.bill.invoice.MultipleItemsChecker;
import com.cob.billing.util.BeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

@Service
@Qualifier("ElectronicBillingClaim")
public class ElectronicBillingClaim extends BillingClaim {
    @Autowired
    private RestTemplate restTemplate;
    ElectronicClaimCreator electronicClaimCreator;
    List<Claim> claims;
    @Override
    public void pickClaimProvider() {
        Boolean hasMultipleItems = MultipleItemsChecker.check(invoiceRequest);
        flags= MultipleItemsChecker.getMultipleFlags();
        if(hasMultipleItems)
            electronicClaimCreator = BeanFactory.getBean(CreateElectronicMultipleClaimUseCase.class);
        else
            electronicClaimCreator = BeanFactory.getBean(CreateElectronicSingleClaimUseCase.class);
    }

    @Override
    public void prepareClaim() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        claims = electronicClaimCreator.create(invoiceRequest,flags);

    }

    @Override
    public void submitClaim() throws IOException {
        //submit to Clearing House
        Gson gson = new Gson();
        ClaimUploadRequest claimUploadRequest = new ClaimUploadRequest();
        claimUploadRequest.setClaim(claims);
        Random random = new Random();
        Integer fileID = random.nextInt(900) + 100;
        claimUploadRequest.setFileid(fileID.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("claim_upload_request.json");
        objectMapper.writeValue(file, claimUploadRequest);
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        FileSystemResource fileResource = new FileSystemResource(file);
        builder.part("File", fileResource);
        builder.part("AccountKey", "16355_4HRM#uWBOVXPLZE2qINJbRqz");
        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity =
                new HttpEntity<>(multipartRequest, headers);
        String serverUrl = "https://svc.claim.md/services/upload/";
        ResponseEntity<String> response = restTemplate.exchange(
                serverUrl, HttpMethod.POST, requestEntity, String.class);
        System.out.println("response ");
    }
}
