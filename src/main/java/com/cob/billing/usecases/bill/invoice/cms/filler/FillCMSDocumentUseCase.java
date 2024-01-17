package com.cob.billing.usecases.bill.invoice.cms.filler;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.enums.DateServiceClaim;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.usecases.bill.invoice.cms.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FillCMSDocumentUseCase {
    @Autowired
    MultipleProvidersPerClaimCreator multipleProvidersPerClaimCreator;
    @Autowired
    MultipleClinicsPerClaimCreator multipleClinicsPerClaimCreator;
    @Autowired
    MultipleCasesPerClaimCreator multipleCasesPerClaimCreator;
    @Autowired
    MultipleDatesPerClaimCreator multipleDatesPerClaimCreator;
    @Autowired
    SingleClaimCreator singleClaimCreator;
    DateServiceClaim dateServiceClaim;

    public List<String> fill(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        checkIsDatePerClaim(invoiceRequest.getInvoiceRequestConfiguration());
        List<String> fileNames = new ArrayList<>();
        fileNames.addAll(multipleProvidersPerClaimCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClinicsPerClaimCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleCasesPerClaimCreator.create(invoiceRequest, patientInvoiceRecords));

        if (!(fileNames.size() > 1)) {
            switch (dateServiceClaim) {
                case Per_Date:
                    fileNames.addAll(multipleDatesPerClaimCreator.create(invoiceRequest, patientInvoiceRecords));
                    break;
                case All:
                    fileNames.addAll(singleClaimCreator.create(invoiceRequest, patientInvoiceRecords));
            }
        }
        return fileNames;
    }

    private void checkIsDatePerClaim(InvoiceRequestConfiguration invoiceRequestConfiguration) {
        if (invoiceRequestConfiguration.getIsOneDateServicePerClaim() != null) {
            if (!invoiceRequestConfiguration.getIsOneDateServicePerClaim())
                dateServiceClaim = DateServiceClaim.All;
            if (invoiceRequestConfiguration.getIsOneDateServicePerClaim())
                dateServiceClaim = DateServiceClaim.Per_Date;
        } else {
            dateServiceClaim = DateServiceClaim.All;
        }
    }
}
