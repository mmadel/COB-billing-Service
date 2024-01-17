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
    MultipleClaimsProviderCreator multipleClaimsProviderCreator;
    @Autowired
    MultipleClaimsClinicCreator multipleClaimsClinicCreator;
    @Autowired
    MultipleClaimsCaseCreator multipleClaimsCaseCreator;
    @Autowired
    MultipleClaimsDateCreator multipleClaimsDateCreator;
    @Autowired
    SingleClaimCreator singleClaimCreator;
    @Autowired
    MultipleClaimsLineCreator multipleClaimsLineCreator;

    DateServiceClaim dateServiceClaim;

    public List<String> fill(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {
        checkIsDatePerClaim(invoiceRequest.getInvoiceRequestConfiguration());
        List<String> fileNames = new ArrayList<>();
        fileNames.addAll(multipleClaimsProviderCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsClinicCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsCaseCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsLineCreator.create(invoiceRequest, patientInvoiceRecords));

        if (!(fileNames.size() > 1)) {
            switch (dateServiceClaim) {
                case Per_Date:
                    fileNames.addAll(multipleClaimsDateCreator.create(invoiceRequest, patientInvoiceRecords));
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
