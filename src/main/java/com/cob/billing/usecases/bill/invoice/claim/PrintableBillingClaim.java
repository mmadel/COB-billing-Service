package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.response.ClaimSubmission;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceResponseUseCase;
import com.cob.billing.usecases.bill.invoice.cms.UploadCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.cob.billing.usecases.bill.invoice.cms.creator.multiple.CreateMultipleCMSClaimsUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.single.CreateCMSClaimUseCase;
import com.cob.billing.usecases.bill.invoice.MultipleItemsChecker;
import com.cob.billing.util.BeanFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Qualifier("PrintableBillingClaim")
public class PrintableBillingClaim extends BillingClaim {
    @Autowired
    CreateInvoiceResponseUseCase createInvoiceResponseUseCase;
    CMSClaimCreator cmsClaimCreator;
    List<String> files;
    Map<String, List<SelectedSessionServiceLine>> result = new HashMap<>();

    @Override
    public void pickClaimProvider() {
        Boolean hasMultipleItems = MultipleItemsChecker.check(invoiceRequest);
        flags = MultipleItemsChecker.getMultipleFlags();
        if (hasMultipleItems) {
            cmsClaimCreator = BeanFactory.getBean(CreateMultipleCMSClaimsUseCase.class);
        } else {
            cmsClaimCreator = BeanFactory.getBean(CreateCMSClaimUseCase.class);
        }
    }

    @Override
    public void createClaim() throws IOException, IllegalAccessException {
        result = cmsClaimCreator.create(invoiceRequest, flags);
    }

    @Override
    public void submitClaim() throws IOException {
        PdfWriter writer = new PdfWriter(invoiceRequest.getResponse().getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        List<ClaimSubmission> claimSubmissions = new ArrayList<>();
        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : result.entrySet()) {
            File tmpFile = new File(entry.getKey());
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
            createInvoiceResponseUseCase.create(claimSubmissions,entry.getValue());
        }
        invoiceResponse.setClaimSubmissions(claimSubmissions);
        merger.close();
        UploadCMSFileUseCase uploadCMSFileUseCase = BeanFactory.getBean(UploadCMSFileUseCase.class);
        uploadCMSFileUseCase.persist(result.keySet().stream().collect(Collectors.toList()), invoiceRequest.getRecords());

    }
}
