package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateCMSDocumentUseCase {
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

    public List<String> createCMSDocument(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws IOException {

        List<String> fileNames = new ArrayList<>();
        fileNames.addAll(multipleClaimsProviderCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsClinicCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsCaseCreator.create(invoiceRequest, patientInvoiceRecords));
        fileNames.addAll(multipleClaimsDateCreator.create(invoiceRequest, patientInvoiceRecords));

        if (!(fileNames.size() > 1)) {
            fileNames.addAll(singleClaimCreator.create(patientInvoiceRecords, invoiceRequest));
        }
        return fileNames;
    }

}
