package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.response.ClaimSubmission;
import com.cob.billing.usecases.bill.invoice.CreateCMSFileUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceResponseUseCase;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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
    @Autowired
    CreateCMSFileUseCase createCMSFileUseCase;

    @Override
    public void pickClaimProvider() {
        createCMSFileUseCase.pickClaimCreator(invoiceRequest);
    }

    @Override
    public void createClaim() throws IOException, IllegalAccessException {
        createCMSFileUseCase.createClaim(invoiceRequest, invoiceResponse);
    }

    @Override
    public void submitClaim() throws IOException {
        PdfWriter writer = new PdfWriter(invoiceRequest.getResponse().getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        List<String> fileNames = createCMSFileUseCase.getClaimsFileName();
        for (String fileName : fileNames) {
            File tmpFile = new File(fileName);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
        }
        merger.close();
//        List<ClaimSubmission> claimSubmissions = new ArrayList<>();
//        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : invoiceRequest.getFileNamesServiceLinesMapper().entrySet()) {
//            createInvoiceResponseUseCase.create(claimSubmissions, entry.getValue());
//        }
//        invoiceResponse.setClaimSubmissions(claimSubmissions);
    }
}
