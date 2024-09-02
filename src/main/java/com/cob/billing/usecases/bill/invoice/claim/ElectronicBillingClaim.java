package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.model.integration.claimmd.submit.SubmitRequest;
import com.cob.billing.model.integration.claimmd.submit.SubmitResponse;
import com.cob.billing.usecases.bill.invoice.CreateCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.usecases.bill.invoice.electronic.creator.single.CreateElectronicSingleClaimUseCase;
import com.cob.billing.usecases.integration.claim.md.SubmitClaimUseCase;
import com.cob.billing.util.BeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Qualifier("ElectronicBillingClaim")
public class ElectronicBillingClaim extends BillingClaim {

    ElectronicClaimCreator electronicClaimCreator;
    List<Claim> claims;

    @Autowired
    CreateCMSFileUseCase createCMSFileUseCase;

    @Autowired
    SubmitClaimUseCase submitClaimUseCase;

    @Override
    public void pickClaimProvider() {
        electronicClaimCreator = BeanFactory.getBean(CreateElectronicSingleClaimUseCase.class);
    }

    @Override
    public void createClaim() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        claims = electronicClaimCreator.create(invoiceRequest, flags, invoiceResponse);

    }

    @Override
    public void submitClaim() throws IOException, IllegalAccessException {
        //submit to Clearing House
        SubmitRequest submitRequest = constructClaimRequest();
        FileSystemResource claimResource = createClaimFile(submitRequest);
        SubmitResponse response = submitClaimUseCase.submit(claimResource);
        prepareInvoiceResponse(response);
        //claimResource.getFile().delete();

    }

    private SubmitRequest constructClaimRequest() {
        Random random = new Random();
        Integer fileId = random.nextInt(900) + 100;
        return SubmitRequest.builder()
                .claim(claims)
                .fileid(fileId.toString())
                .build();
    }

    private FileSystemResource createClaimFile(SubmitRequest submitRequest) throws IOException {
        String fileName = "claim_" + new Date().getTime() + ".json";
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, submitRequest);
        return new FileSystemResource(file);
    }


    private void prepareInvoiceResponse(SubmitResponse submitResponse) throws IOException, IllegalAccessException {
        invoiceResponse.setClearingHouseClaimsResponse(submitResponse);
        createCMSFileUseCase.createClaim(invoiceRequest, invoiceResponse);
    }
}
