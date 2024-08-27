package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.response.ClaimSubmission;
import com.cob.billing.usecases.bill.invoice.CreateCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceResponseUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("PrintableBillingClaim")
public class PrintableBillingClaim extends BillingClaim {
    @Autowired
    CreateInvoiceResponseUseCase createInvoiceResponseUseCase;
    CMSClaimCreator cmsClaimCreator;
    List<String> files;
    Map<String, List<SelectedSessionServiceLine>> result = new HashMap<>();
    @Autowired
    CreateCMSFileUseCase createCMSFileUseCase;

    @Override
    public void pickClaimProvider() {
        createCMSFileUseCase.pickClaimCreator(invoiceRequest);
    }

    @Override
    public void createClaim() throws IOException, IllegalAccessException {
        createCMSFileUseCase.createClaim(invoiceRequest);
    }

    @Override
    public void submitClaim() throws IOException {
        createCMSFileUseCase.createPdfMerger(invoiceRequest.getResponse().getOutputStream());
        createCMSFileUseCase.generateCMSFile(invoiceRequest);
        List<ClaimSubmission> claimSubmissions = new ArrayList<>();
        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : invoiceRequest.getFileNamesServiceLinesMapper().entrySet()) {
            createInvoiceResponseUseCase.create(claimSubmissions, entry.getValue());
        }
        invoiceResponse.setClaimSubmissions(claimSubmissions);
    }
}
