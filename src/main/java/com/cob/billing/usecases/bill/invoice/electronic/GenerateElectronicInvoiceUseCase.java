package com.cob.billing.usecases.bill.invoice.electronic;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.integration.claimmd.ClaimUploadRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class GenerateElectronicInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;

    @Autowired
    CreateElectronicDocumentUseCase createElectronicDocumentUseCase;

    public ClaimUploadRequest generate(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
        ClaimUploadRequest claimUploadRequest = new ClaimUploadRequest();
        claimUploadRequest.setClaim(createElectronicDocumentUseCase.createDocument(invoiceRequest));
        return claimUploadRequest;
    }
}
