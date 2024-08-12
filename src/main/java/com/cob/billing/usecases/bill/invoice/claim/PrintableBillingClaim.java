package com.cob.billing.usecases.bill.invoice.claim;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Qualifier("PrintableBillingClaim")
public class PrintableBillingClaim extends BillingClaim {
    CMSClaimCreator cmsClaimCreator;
    List<String> files;
    @Override
    public void pickClaimProvider() {
        Boolean hasMultipleItems = MultipleItemsChecker.check(invoiceRequest);
        flags= MultipleItemsChecker.getMultipleFlags();
        if(hasMultipleItems) {
            cmsClaimCreator= BeanFactory.getBean(CreateMultipleCMSClaimsUseCase.class);
        }
        else{
            cmsClaimCreator = BeanFactory.getBean(CreateCMSClaimUseCase.class);
        }
    }

    @Override
    public void prepareClaim() throws IOException, IllegalAccessException {
        files= cmsClaimCreator.create(invoiceRequest,flags);
    }

    @Override
    public void submitClaim() throws IOException {
        PdfWriter writer = new PdfWriter(invoiceRequest.getResponse().getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdf);
        for (String file : files) {
            File tmpFile = new File(file);
            PdfReader source = new PdfReader(tmpFile);
            PdfDocument sourceDoc = new PdfDocument(source);
            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
            sourceDoc.close();
        }
        merger.close();
        UploadCMSFileUseCase uploadCMSFileUseCase = BeanFactory.getBean(UploadCMSFileUseCase.class);
        uploadCMSFileUseCase.persist(files, invoiceRequest.getRecords());

    }
}
